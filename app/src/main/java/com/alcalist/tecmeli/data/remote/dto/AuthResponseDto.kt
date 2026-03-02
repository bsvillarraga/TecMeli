package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Objeto de Transferencia de Datos (DTO) que representa la respuesta del servidor tras una autenticación exitosa.
 *
 * @property accessToken Token de acceso de corta duración utilizado para autorizar peticiones a la API.
 * @property tokenType Tipo de token retornado (ej: "Bearer").
 * @property expiresIn Tiempo de vida del Access Token en segundos.
 * @property scope Alcance de los permisos otorgados para este token.
 * @property userId Identificador único del usuario autenticado en Mercado Libre.
 * @property refreshToken Token de larga duración utilizado para renovar el Access Token sin intervención del usuario.
 */
data class AuthResponseDto(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Long,
    @SerializedName("scope") val scope: String,
    @SerializedName("user_id") val userId: Long,
    @SerializedName("refresh_token") val refreshToken: String
)
