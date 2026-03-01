package com.alcalist.tecmeli.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alcalist.tecmeli.core.util.UiState
import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Empty)
    val uiState: StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    fun searchProducts(query: String) {
        if (query.isBlank()) {
            _uiState.value = UiState.Empty
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            
            getProductsUseCase(query)
                .onSuccess { products ->
                    _uiState.value = if (products.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(products)
                    }
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        message = error.localizedMessage ?: "Ocurrió un error inesperado",
                        exception = error
                    )
                }
        }
    }
}
