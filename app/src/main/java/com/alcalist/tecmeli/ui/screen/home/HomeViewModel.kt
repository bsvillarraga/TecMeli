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

/**
 * ViewModel encargado de la lógica de negocio para la pantalla de inicio (Home).
 *
 * Su responsabilidad es gestionar la búsqueda de productos y exponer el estado de la UI
 * mediante un [StateFlow] reactivo. Utiliza el caso de uso [GetProductsUseCase] para obtener los datos.
 *
 * @property getProductsUseCase Caso de uso para realizar la búsqueda de productos.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    /** Estado interno de la UI, mutable solo dentro del ViewModel. */
    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Empty)
    
    /** 
     * Estado público de la UI expuesto como un flujo de datos inmutable.
     * La UI debe observar este campo para reaccionar a cambios en el estado (Cargando, Éxito, Error).
     */
    val uiState: StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    /**
     * Inicia una búsqueda de productos basada en el texto ingresado.
     *
     * Si la consulta es vacía o contiene solo espacios, el estado se establece en [UiState.Empty].
     * En otro caso, lanza una corrutina en el [viewModelScope] para realizar la petición asíncrona.
     *
     * @param query El término de búsqueda ingresado por el usuario.
     */
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
