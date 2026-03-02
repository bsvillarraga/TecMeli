package com.alcalist.tecmeli.data.repository

import android.util.Log
import com.alcalist.tecmeli.core.network.ApiConfig
import com.alcalist.tecmeli.data.remote.api.AuthApi
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenRepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var authApi: AuthApi
    private lateinit var tokenRepository: TokenRepositoryImpl
    private val apiConfig = ApiConfig(
        clientId = "test_id",
        clientSecret = "test_secret",
        refreshToken = "test_refresh"
    )

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authApi = retrofit.create(AuthApi::class.java)
        tokenRepository = TokenRepositoryImpl(authApi, apiConfig)
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
        mockWebServer.shutdown()
    }

    @Test
    fun `getAccessToken returns token on successful refresh`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{"access_token":"new_access_token","token_type":"Bearer","expires_in":3600,"refresh_token":"new_refresh_token","scope":"read write","user_id":12345}""")
        )

        val result = tokenRepository.refreshToken()

        assertTrue(result.isSuccess)
        assertEquals("new_access_token", result.getOrNull())
        assertEquals("new_access_token", tokenRepository.getAccessToken())
    }

    @Test
    fun `getAccessToken returns null when refresh fails`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(401).setBody("""{"error":"invalid_grant"}""")
        )

        val result = tokenRepository.refreshToken()

        assertTrue(result.isFailure)
        assertNull(tokenRepository.getAccessToken())
    }

    @Test
    fun `getAccessToken triggers refresh on first call`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{"access_token":"cached_token","token_type":"Bearer","expires_in":3600,"refresh_token":"new_refresh","scope":"read","user_id":12345}""")
        )

        val token = tokenRepository.getAccessToken()

        assertNotNull(token)
        assertEquals("cached_token", token)
    }
}
