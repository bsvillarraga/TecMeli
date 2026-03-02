package com.alcalist.tecmeli.domain.usecase

import com.alcalist.tecmeli.domain.model.ProductDetail
import com.alcalist.tecmeli.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetProductDetailUseCaseTest {
    private val repository: ProductRepository = mockk()
    private val useCase = GetProductDetailUseCase(repository)

    @Test
    fun `Invoke return product detail when success`() = runTest {
        val productDetail = ProductDetail(id = "1", title = "Title", description = "Description")
        coEvery { repository.getProductDetail(any()) } returns Result.success(productDetail)

        val result = useCase("id")
        assert(result.isSuccess)
        assertEquals(productDetail, result.getOrNull())
    }

    @Test
    fun `Invoke return failure when repository fails`() = runTest {
        coEvery { repository.getProductDetail(any()) } returns Result.failure(Exception("fail"))

        val result = useCase("id")
        assert(result.isFailure)
    }
}