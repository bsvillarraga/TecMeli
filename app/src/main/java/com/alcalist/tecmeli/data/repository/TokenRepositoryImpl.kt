package com.alcalist.tecmeli.data.repository

import com.alcalist.tecmeli.core.network.ApiConfig
import com.alcalist.tecmeli.core.network.safeApiCall
import com.alcalist.tecmeli.data.remote.api.AuthApi
import com.alcalist.tecmeli.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val apiConfig: ApiConfig
) : TokenRepository {

    private var accessToken: String? = null

    override fun getAccessToken(): String? {
        if (accessToken == null) {
            runBlocking {
                refreshToken()
            }
        }
        return accessToken
    }

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
