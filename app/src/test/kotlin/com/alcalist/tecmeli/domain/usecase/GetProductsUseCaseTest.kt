package com.alcalist.tecmeli.domain.usecase

import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProductsUseCaseTest {

    private val repository: ProductRepository = mockk()
    private val useCase = GetProductsUseCase(repository)

    @Test
    fun `invoke returns empty list when query is blank`() = runTest {
        val result = useCase("")
        assert(result.isSuccess)
        assertEquals(emptyList<Product>(), result.getOrNull())
    }

    @Test
    fun `invoke delegates to repository when query is not blank`() = runTest {
        val products = listOf(Product("1", "Title"))
        coEvery { repository.searchProducts("term") } returns Result.success(products)

        val result = useCase("term")

        assert(result.isSuccess)
        assertEquals(products, result.getOrNull())
    }
}

