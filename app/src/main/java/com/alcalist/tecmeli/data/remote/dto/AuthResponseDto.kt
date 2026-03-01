package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Long,
    @SerializedName("scope") val scope: String,
    @SerializedName("user_id") val userId: Long,
    @SerializedName("refresh_token") val refreshToken: String
)
