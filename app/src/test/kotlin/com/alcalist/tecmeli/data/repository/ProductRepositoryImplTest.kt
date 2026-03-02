package com.alcalist.tecmeli.data.repository

import android.util.Log
import com.alcalist.tecmeli.data.remote.api.MeliApi
import com.alcalist.tecmeli.data.remote.dto.AttributesDto
import com.alcalist.tecmeli.data.remote.dto.ResultsDto
import com.alcalist.tecmeli.data.remote.dto.SearchResponseDto
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

class ProductRepositoryImplTest {

    private val api: MeliApi = mockk()
    private val repository = ProductRepositoryImpl(api)

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
    fun `searchProducts returns success when api returns successful response`() = runTest {
        val dto = ResultsDto(
            id = "1",
            catalogProductId = "cp1",
            domainId = "d1",
            name = "Title",
            attributes = arrayListOf(),
            shortDescription = null,
            pictures = arrayListOf(),
            lastUpdated = null
        )
        val responseDto = SearchResponseDto(results = arrayListOf(dto))
        coEvery { 
            api.searchProducts(
                status = any(),
                siteId = any(),
                query = "term"
            ) 
        } returns Response.success(responseDto)

        val result = repository.searchProducts("term")

        assertTrue(result.isSuccess)
        val products = result.getOrNull()
        assertNotNull(products)
        assertEquals(1, products?.size)
        assertEquals("1", products?.first()?.id)
    }

    @Test
    fun `searchProducts returns failure when api returns error`() = runTest {
        val errorResponse: Response<SearchResponseDto> =
            Response.error(401, "".toResponseBody(null))
        
        coEvery { 
            api.searchProducts(
                status = any(),
                siteId = any(),
                query = "term"
            ) 
        } returns errorResponse

        val result = repository.searchProducts("term")

        assertTrue(result.isFailure)
    }

    @Test
    fun `getProductDetail returns success when api returns product`() = runTest {
        val dto = ResultsDto(
            id = "1",
            catalogProductId = "cp1",
            domainId = "d1",
            name = "Title",
            attributes = arrayListOf(AttributesDto("1", "Name", "Value")),
            shortDescription = null,
            pictures = arrayListOf(),
            lastUpdated = null
        )
        val responseDto = SearchResponseDto(results = arrayListOf(dto))
        coEvery { 
            api.getProductById(
                siteId = any(),
                productId = "1"
            ) 
        } returns Response.success(responseDto)

        val result = repository.getProductDetail("1")
        assertTrue(result.isSuccess)
    }

    @Test
    fun `getProductDetail returns failure when product not found`() = runTest {
        val responseDto = SearchResponseDto(results = arrayListOf())
        coEvery { 
            api.getProductById(
                siteId = any(),
                productId = "1"
            ) 
        } returns Response.success(responseDto)

        val result = repository.getProductDetail("1")

        assertTrue(result.isFailure)
    }
}
