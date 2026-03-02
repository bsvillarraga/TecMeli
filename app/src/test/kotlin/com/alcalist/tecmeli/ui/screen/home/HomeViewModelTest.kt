package com.alcalist.tecmeli.ui.screen.home

import com.alcalist.tecmeli.core.util.UiState
import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.usecase.GetProductsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getProductsUseCase: GetProductsUseCase = mockk()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(getProductsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchProducts emits Loading and Success when repository returns products`() = runTest {
        val products = listOf(Product("1", "Title"))
        coEvery { getProductsUseCase("term") } returns Result.success(products)

        viewModel.searchProducts("term")

        // advance until the coroutines finish
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
    }

    @Test
    fun `searchProducts emits Loading and Empty when repository returns empty list`() = runTest {
        val products = emptyList<Product>()
        coEvery { getProductsUseCase("term") } returns Result.success(products)

        viewModel.searchProducts("term")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Empty)
    }

    @Test
    fun `searchProducts emits Loading and Empty when query is blank`() = runTest {
        viewModel.searchProducts("")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Empty)
    }

    @Test
    fun `searchProducts emits Loading and Error when repository fails`() = runTest {
        coEvery { getProductsUseCase("term") } returns Result.failure(Exception("fail"))

        viewModel.searchProducts("term")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Error)
    }
}
