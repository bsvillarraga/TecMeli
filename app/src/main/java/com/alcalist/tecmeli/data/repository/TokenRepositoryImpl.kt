package com.alcalist.tecmeli.data.repository

import com.alcalist.tecmeli.core.network.ApiConfig
import com.alcalist.tecmeli.core.network.safeApiCall
import com.alcalist.tecmeli.data.remote.api.AuthApi
import com.alcalist.tecmeli.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación de [TokenRepository] encargada de la gestión y persistencia en memoria del Access Token.
 *
 * Esta clase utiliza [AuthApi] para renovar los tokens cuando sea necesario y mantiene
 * una caché simple en memoria del token actual. Está marcada como [@Singleton] para asegurar
 * que toda la aplicación comparta el mismo estado de autenticación.
 *
 * @property authApi Interfaz de red para llamadas de autenticación.
 * @property apiConfig Configuración que contiene las credenciales de la aplicación.
 */
@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val apiConfig: ApiConfig
) : TokenRepository {

    /** Token de acceso actualmente en caché de memoria. */
    private var accessToken: String? = null

    /**
     * Retorna el token de acceso actual.
     *
     * Si el token es nulo, intenta realizar una renovación síncrona mediante [runBlocking].
     * **Nota:** El uso de runBlocking aquí es intencional para integrarse con interceptores
     * de OkHttp que requieren una respuesta inmediata.
     *
     * @return El Access Token actual o null si no se pudo obtener.
     */
    override fun getAccessToken(): String? {
        if (accessToken == null) {
            runBlocking {
                refreshToken()
            }
        }
        return accessToken
    }

    /**
     * Ejecuta la petición de refresco de token contra el servidor.
     *
     * Utiliza [safeApiCall] para gestionar errores de red y, en caso de éxito,
     * actualiza la variable [accessToken] en memoria.
     *
     * @return [Result] con el nuevo token o un error de aplicación.
     */
    override suspend fun refreshToken(): Result<String> = safeApiCall(
        call = {
            authApi.refreshToken(
                clientId = apiConfig.clientId,
                clientSecret = apiConfig.clientSecret,
                refreshToken = apiConfig.refreshToken
            )
        },
        transform = { body ->
            accessToken = body.accessToken
            body.accessToken
        }
    )
}
