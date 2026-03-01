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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.mercadolibre.com/"

    @Provides
    @Singleton
    fun provideApiConfig(): ApiConfig {
        return ApiConfig(
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET,
            refreshToken = BuildConfig.REFRESH_TOKEN
        )
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

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

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMeliApi(retrofit: Retrofit): MeliApi {
        return retrofit.create(MeliApi::class.java)
    }

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
