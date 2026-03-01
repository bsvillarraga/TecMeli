package com.alcalist.tecmeli.core.network

import com.alcalist.tecmeli.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Skip auth for token refresh endpoint if needed, 
        // though usually it's a different base URL or handled by no-auth client.
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
