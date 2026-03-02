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

/**
 * ViewModel encargado de gestionar el estado y la lógica de la pantalla de detalle de producto.
 *
 * Utiliza el caso de uso [GetProductDetailUseCase] para obtener la información completa
 * de un producto específico y expone un [UiState] para que la vista reaccione a los cambios.
 *
 * @property getProductDetailUseCase Caso de uso para obtener los detalles técnicos y descripción del producto.
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    /** Estado mutable privado que encapsula el estado de carga, éxito o error del detalle. */
    private val _uiState = MutableStateFlow<UiState<ProductDetail>>(UiState.Empty)

    /** 
     * Flujo de estado público observado por la UI.
     * Representa de forma reactiva la información del detalle del producto.
     */
    val uiState: StateFlow<UiState<ProductDetail>> = _uiState.asStateFlow()

    /**
     * Solicita la información detallada de un producto por su identificador.
     *
     * Inicia una corrutina en el [viewModelScope], establece el estado inicial en [UiState.Loading]
     * y actualiza el estado basándose en el resultado del caso de uso.
     *
     * @param id Identificador único del producto (ej: "MCO12345").
     */
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
