package com.alcalist.tecmeli.core.network

import com.alcalist.tecmeli.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor de OkHttp encargado de inyectar el token de acceso en las peticiones salientes.
 *
 * Su función principal es interceptar cada solicitud HTTP dirigida a la API de Mercado Libre
 * y añadir la cabecera "Authorization: Bearer {token}" si existe un token disponible
 * en el [TokenRepository].
 */
class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {

    /**
     * Intercepta la cadena de ejecución de la petición.
     *
     * @param chain La cadena de interceptores de OkHttp.
     * @return [Response] La respuesta del servidor tras procesar la petición con el token.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Evita añadir el token si la petición es para el endpoint de autenticación
        if (originalRequest.url.encodedPath.contains("oauth/token")) {
            return chain.proceed(originalRequest)
        }

        val token = tokenRepository.getAccessToken()
        val requestBuilder = originalRequest.newBuilder()
        
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        
        return chain.proceed(requestBuilder.build())
    }
}
