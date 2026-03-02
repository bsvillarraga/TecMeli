package com.alcalist.tecmeli.data.remote.api

import com.alcalist.tecmeli.data.remote.dto.AuthResponseDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Interfaz de Retrofit que define los endpoints para el proceso de autenticación de Mercado Libre.
 *
 * Se encarga de gestionar la obtención y renovación de tokens de acceso mediante el flujo OAuth 2.0.
 */
interface AuthApi {
    /**
     * Solicita un nuevo token de acceso utilizando un token de actualización (refresh token).
     *
     * @param grantType Tipo de concesión (por defecto "refresh_token").
     * @param clientId Identificador de la aplicación cliente.
     * @param clientSecret Clave secreta de la aplicación cliente.
     * @param refreshToken Token de actualización previamente obtenido.
     * @return [Response] que contiene un [AuthResponseDto] con los nuevos tokens.
     */
    @POST("oauth/token")
    @FormUrlEncoded
    suspend fun refreshToken(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("refresh_token") refreshToken: String
    ): Response<AuthResponseDto>
}
