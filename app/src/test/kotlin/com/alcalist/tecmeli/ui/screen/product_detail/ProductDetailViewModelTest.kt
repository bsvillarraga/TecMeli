package com.alcalist.tecmeli.ui.screen.product_detail

import com.alcalist.tecmeli.core.util.UiState
import com.alcalist.tecmeli.domain.model.ProductAttribute
import com.alcalist.tecmeli.domain.model.ProductDetail
import com.alcalist.tecmeli.domain.usecase.GetProductDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val getProductDetailUseCase: GetProductDetailUseCase = mockk()
    private lateinit var viewModel: ProductDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductDetailViewModel(getProductDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getProductDetail emits Loading and Success when repository returns product detail`() =
        runTest {
            val productDetail = ProductDetail(
                "1", "Title", description = "Description", attributes = listOf(
                    ProductAttribute("1", "Name", "Value")
                )
            )
            coEvery { getProductDetailUseCase("id") } returns Result.success(productDetail)

            viewModel.getProductDetail("id")
            testDispatcher.scheduler.advanceUntilIdle()

            val result = viewModel.uiState.value
            assertTrue(result is UiState.Success)
            assertTrue((result as UiState.Success).data == productDetail)
            assertTrue(result.data.attributes.first().name == "Name")
        }

    @Test
    fun `getProductDetail emits Loading and Error when repository fails`() = runTest {
        coEvery { getProductDetailUseCase("id") } returns Result.failure(Exception("fail"))

        viewModel.getProductDetail("id")
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.uiState.value
        assertTrue(result is UiState.Error)
    }
}