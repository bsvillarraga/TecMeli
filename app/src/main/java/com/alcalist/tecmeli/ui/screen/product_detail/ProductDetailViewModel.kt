package com.alcalist.tecmeli.ui.screen.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alcalist.tecmeli.core.util.UiState
import com.alcalist.tecmeli.domain.model.ProductDetail
import com.alcalist.tecmeli.domain.usecase.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ProductDetail>>(UiState.Empty)
    val uiState: StateFlow<UiState<ProductDetail>> = _uiState.asStateFlow()

    fun getProductDetail(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getProductDetailUseCase(id)
                .onSuccess { detail ->
                    _uiState.value = UiState.Success(detail)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        message = error.localizedMessage ?: "Error al cargar el detalle",
                        exception = error
                    )
                }
        }
    }
}
