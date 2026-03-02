package com.alcalist.tecmeli.core.network.mapper

import com.alcalist.tecmeli.core.network.AppError
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Implementación estándar para el mapeo de excepciones de red.
 *
 * Esta clase se encarga de categorizar excepciones comunes de Java/Kotlin y Retrofit
 * en los tipos definidos por [AppError], facilitando el manejo consistente de errores
 * a lo largo de toda la aplicación.
 */
class DefaultErrorMapper : ErrorMapper {

    /**
     * Mapea una excepción a un tipo específico de [AppError].
     *
     * @param exception La excepción a evaluar.
     * @return [AppError.Timeout] si se excedió el tiempo de espera.
     * @return [AppError.Network] para problemas generales de conectividad IO.
     * @return [AppError.Unknown] para cualquier otra excepción no prevista.
     */
    override fun mapException(exception: Exception): AppError = when (exception) {
        is SocketTimeoutException -> AppError.Timeout()
        is IOException -> AppError.Network()
        else -> AppError.Unknown(exception)
    }
}
