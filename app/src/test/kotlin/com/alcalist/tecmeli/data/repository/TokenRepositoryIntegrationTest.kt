package com.alcalist.tecmeli.data.repository

import android.util.Log
import com.alcalist.tecmeli.core.network.ApiConfig
import com.alcalist.tecmeli.data.remote.api.AuthApi
import com.alcalist.tecmeli.data.remote.dto.AuthResponseDto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class TokenRepositoryIntegrationTest {
    private val authApi: AuthApi = mockk()

    private val apiConfig = ApiConfig(
        clientId = "test_id",
        clientSecret = "test_secret",
        refreshToken = "test_refresh"
    )
    private val tokenRepository = TokenRepositoryImpl(authApi, apiConfig)

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `refreshToken returns success with valid response`() = runTest {
        val mockResponse = AuthResponseDto(
            accessToken = "new_access_token",
            tokenType = "Bearer",
            expiresIn = 3600,
            refreshToken = "new_refresh_token",
            scope = "read write",
            userId = 12345
        )

        coEvery { authApi.refreshToken(any(), any(), any(), any()) } returns Response.success(mockResponse)

        val result = tokenRepository.refreshToken()

        assertTrue(result.isSuccess)
        assertEquals("new_access_token", result.getOrNull())
        assertEquals("new_access_token", tokenRepository.getAccessToken())
    }

    @Test
    fun `refreshToken returns failure on API error`() = runTest {
        coEvery { authApi.refreshToken(any(), any(), any(), any()) } returns Response.error(401, "".toResponseBody(null))

        val result = tokenRepository.refreshToken()

        assertTrue(result.isFailure)
    }

    @Test
    fun `getAccessToken caches token after refresh`() = runTest {
        val mockResponse = AuthResponseDto(
            accessToken = "cached_token",
            tokenType = "Bearer",
            expiresIn = 3600,
            refreshToken = "new_refresh",
            scope = "read",
            userId = 12345
        )

        coEvery { authApi.refreshToken(any(), any(), any(), any()) } returns Response.success(mockResponse)

        val token = tokenRepository.getAccessToken()

        assertNotNull(token)
        assertEquals("cached_token", token)
    }
}
