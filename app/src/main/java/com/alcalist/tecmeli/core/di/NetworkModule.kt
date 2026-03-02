package com.alcalist.tecmeli.core.di

import com.alcalist.tecmeli.BuildConfig
import com.alcalist.tecmeli.core.network.ApiConfig
import com.alcalist.tecmeli.core.network.AuthInterceptor
import com.alcalist.tecmeli.core.network.TokenAuthenticator
import com.alcalist.tecmeli.data.remote.api.AuthApi
import com.alcalist.tecmeli.data.remote.api.MeliApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Módulo de Hilt encargado de proveer las dependencias relacionadas con la red.
 *
 * Configura el cliente HTTP, los interceptores de seguridad, el autenticador
 * y las instancias de Retrofit para las diferentes APIs de la aplicación.
 * Todas las instancias se mantienen como [Singleton] para optimizar recursos.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.mercadolibre.com/"

    /**
     * Provee la configuración base de la API extrayendo valores de [BuildConfig].
     */
    @Provides
    @Singleton
    fun provideApiConfig(): ApiConfig {
        return ApiConfig(
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET,
            refreshToken = BuildConfig.REFRESH_TOKEN
        )
    }

    /**
     * Configura un interceptor para registrar las peticiones y respuestas HTTP en consola.
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Configura el cliente principal de OkHttp con seguridad y logs.
     *
     * @param authInterceptor Agrega el token de acceso a las cabeceras.
     * @param tokenAuthenticator Gestiona la renovación automática de tokens (401).
     * @param loggingInterceptor Registra el tráfico de red.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Provee la instancia base de Retrofit configurada con Gson.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Crea la implementación de la API de productos de Mercado Libre.
     */
    @Provides
    @Singleton
    fun provideMeliApi(retrofit: Retrofit): MeliApi {
        return retrofit.create(MeliApi::class.java)
    }

    /**
     * Crea una instancia de Retrofit específica para procesos de autenticación.
     *
     * Se utiliza un cliente de OkHttp simplificado (sin interceptor de auth) para evitar
     * bucles infinitos durante la renovación del token.
     */
    @Provides
    @Singleton
    fun provideAuthApi(loggingInterceptor: HttpLoggingInterceptor): AuthApi {
        val authClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}
