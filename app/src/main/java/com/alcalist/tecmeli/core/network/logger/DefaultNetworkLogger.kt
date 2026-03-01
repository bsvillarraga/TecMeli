package com.alcalist.tecmeli.core.network.logger

import android.util.Log

/**
 * Implementación por defecto del NetworkLogger usando Android Log.
 */
class DefaultNetworkLogger : NetworkLogger {
    override fun logServerError(code: Int, message: String?) {
        Log.e("Network", "Error de servidor: $code - $message")
    }

    override fun logUnexpectedError(errorType: String, exception: Exception) {
        Log.e("Network", "Error inesperado: $errorType", exception)
    }
}

