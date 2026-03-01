package com.alcalist.tecmeli.core.network.logger

/**
 * Interfaz para manejar el logging de errores de red.
 * Responsabilidad: registrar errores en el sistema.
 */
interface NetworkLogger {
    fun logServerError(code: Int, message: String?)
    fun logUnexpectedError(errorType: String, exception: Exception)
}

