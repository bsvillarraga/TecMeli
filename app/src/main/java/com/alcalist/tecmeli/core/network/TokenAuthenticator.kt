package com.alcalist.tecmeli.core.network

import com.alcalist.tecmeli.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

/**
 * Autenticador de OkHttp encargado de la renovación automática de tokens.
 *
 * Cuando el servidor responde con un error 401 (No autorizado), este componente intercepta
 * la respuesta e intenta refrescar el token de acceso de forma síncrona mediante el [TokenRepository].
 * Si la renovación es exitosa, reintenta la petición original con el nuevo token.
 */
class TokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository
) : Authenticator {

    /**
     * Responde a un desafío de autenticación del servidor.
     *
     * @param route La ruta que falló.
     * @param response La respuesta que causó el desafío (esperado 401).
     * @return Una nueva [Request] con el token actualizado, o null si falla la renovación.
     */
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != 401) return null

        val result = runBlocking {
            tokenRepository.refreshToken()
        }

        return if (result.isSuccess) {
            val newToken = result.getOrNull()
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            null
        }
    }
}
