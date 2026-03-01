package com.alcalist.tecmeli.domain.repository

interface TokenRepository {
    fun getAccessToken(): String?
    suspend fun refreshToken(): Result<String>
}
