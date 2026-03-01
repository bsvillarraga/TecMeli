package com.alcalist.tecmeli.core.network.handler

import retrofit2.Response

/**
 * Interfaz para procesar respuestas HTTP.
 * Responsabilidad: validar y extraer el cuerpo de respuestas HTTP.
 */
interface HttpResponseHandler {
    fun <T, R> handleResponse(
        response: Response<T>,
        transform: (T) -> R
    ): Result<R>
}

