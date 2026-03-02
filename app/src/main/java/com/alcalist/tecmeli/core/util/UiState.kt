package com.alcalist.tecmeli.core.util

/**
 * Representa los diferentes estados de la interfaz de usuario (UI).
 *
 * Esta clase sellada (sealed class) facilita la gestión del flujo de datos entre el ViewModel
 * y la UI, permitiendo reaccionar a cambios de estado de forma reactiva y segura.
 *
 * @param T El tipo de dato que se espera recibir en caso de éxito.
 */
sealed class UiState<out T> {
    /** Indica que se está realizando una operación asíncrona (ej: carga de red). */
    object Loading : UiState<Nothing>()

    /**
     * Representa un estado de éxito con datos.
     * @property data El contenido obtenido tras la operación exitosa.
     */
    data class Success<T>(val data: T) : UiState<T>()

    /**
     * Representa un fallo en la operación.
     * @property message Descripción legible del error para el usuario.
     * @property exception La causa técnica opcional del error.
     */
    data class Error(val message: String, val exception: Throwable? = null) : UiState<Nothing>()

    /** Indica que la operación terminó con éxito pero no devolvió resultados (lista vacía, etc.). */
    object Empty : UiState<Nothing>()
}
