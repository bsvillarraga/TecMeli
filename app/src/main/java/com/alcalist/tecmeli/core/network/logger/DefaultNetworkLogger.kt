package com.alcalist.tecmeli.core.network.logger

import android.util.Log

/**
 * Implementación de [NetworkLogger] para el ecosistema de Android.
 *
 * Utiliza la clase estándar [android.util.Log] para emitir mensajes en el sistema
 * de registro (Logcat). Esta clase está diseñada para ser utilizada durante el desarrollo,
 * pero es fácilmente reemplazable en entornos de producción.
 */
class DefaultNetworkLogger : NetworkLogger {

    /**
     * Emite un registro de error de nivel 'Error' para fallos del servidor.
     *
     * @param code El código de error HTTP.
     * @param message Descripción del error.
     */
    override fun logServerError(code: Int, message: String?) {
        Log.e("Network", "Error de servidor: $code - $message")
    }

    /**
     * Emite un registro de error de nivel 'Error' para excepciones inesperadas.
     *
     * @param errorType Nombre o tipo de la excepción.
     * @param exception La instancia de la excepción para registrar el stack trace.
     */
    override fun logUnexpectedError(errorType: String, exception: Exception) {
        Log.e("Network", "Error inesperado: $errorType", exception)
    }
}
