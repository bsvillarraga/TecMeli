package com.alcalist.tecmeli.core.network

import com.alcalist.tecmeli.domain.repository.TokenRepository
import io.mockk.every
import io.mockk.mockk
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AuthInterceptorTest {

    private val tokenRepository: TokenRepository = mockk()
    private val chain: Interceptor.Chain = mockk()
    private lateinit var authInterceptor: AuthInterceptor

    @Before
    fun setup() {
        authInterceptor = AuthInterceptor(tokenRepository)
    }

    @Test
    fun `intercept adds Authorization header when token is present`() {
        val mockRequest = Request.Builder()
            .url("https://api.example.com/test")
            .build()

        val mockResponse = Response.Builder()
            .request(mockRequest)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .build()

        every { tokenRepository.getAccessToken() } returns "valid_token"
        every { chain.request() } returns mockRequest
        every { chain.proceed(any()) } returns mockResponse

        val result = authInterceptor.intercept(chain)

        assertNotNull(result)
        assertEquals(200, result.code)
    }

    @Test
    fun `intercept skips auth for oauth token endpoint`() {
        val mockRequest = Request.Builder()
            .url("https://api.example.com/oauth/token")
            .build()

        val mockResponse = Response.Builder()
            .request(mockRequest)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .build()

        every { chain.request() } returns mockRequest
        every { chain.proceed(mockRequest) } returns mockResponse

        val result = authInterceptor.intercept(chain)

        assertNotNull(result)
        assertEquals(200, result.code)
    }

    @Test
    fun `intercept returns response when no token available`() {
        val mockRequest = Request.Builder()
            .url("https://api.example.com/test")
            .build()

        val mockResponse = Response.Builder()
            .request(mockRequest)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(401)
            .message("Unauthorized")
            .build()

        every { tokenRepository.getAccessToken() } returns null
        every { chain.request() } returns mockRequest
        every { chain.proceed(any()) } returns mockResponse

        val result = authInterceptor.intercept(chain)

        assertNotNull(result)
    }
}

