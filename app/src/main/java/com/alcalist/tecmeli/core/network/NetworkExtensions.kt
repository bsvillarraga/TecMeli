package com.alcalist.tecmeli.core.network

import com.alcalist.tecmeli.core.network.executor.SafeApiCallExecutor
import com.alcalist.tecmeli.core.network.handler.DefaultHttpResponseHandler
import com.alcalist.tecmeli.core.network.logger.DefaultNetworkLogger
import com.alcalist.tecmeli.core.network.mapper.DefaultErrorMapper
import retrofit2.Response

/**
 * Función de alto nivel para ejecutar llamadas de red de forma segura.
 * Mantiene compatibilidad con código existente.
 */
suspend fun <T, R> safeApiCall(
    call: suspend () -> Response<T>,
    transform: (T) -> R
): Result<R> {
    val executor = SafeApiCallExecutor(
        responseHandler = DefaultHttpResponseHandler(),
        errorMapper = DefaultErrorMapper(),
        logger = DefaultNetworkLogger()
    )
    return executor.execute(call, transform)
}
