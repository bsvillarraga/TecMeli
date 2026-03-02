package com.alcalist.tecmeli.core.network.mapper

import com.alcalist.tecmeli.core.network.AppError

/**
 * Define un contrato para la transformación de excepciones técnicas a errores de dominio.
 *
 * Esta interfaz sigue el principio de Inversión de Dependencias (DIP) de SOLID,
 * permitiendo que la capa de red desacople sus componentes de las implementaciones
 * específicas de mapeo.
 */
interface ErrorMapper {
    /**
     * Transforma una excepción de bajo nivel en un objeto [AppError].
     *
     * @param exception La excepción lanzada durante una operación (ej: de Retrofit o de IO).
     * @return Una representación de error propia de la aplicación.
     */
    fun mapException(exception: Exception): AppError
}
