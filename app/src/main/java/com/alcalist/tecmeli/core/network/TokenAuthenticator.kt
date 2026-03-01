package com.alcalist.tecmeli.core.network

import com.alcalist.tecmeli.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // Only try to refresh if it's a 401 error
        if (response.code != 401) return null

        // Try to refresh token synchronously
        val result = runBlocking {
            tokenRepository.refreshToken()
        }

        return if (result.isSuccess) {
            val newToken = result.getOrNull()
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            null // Stop trying if refresh fails
        }
    }
}
