package com.alcalist.tecmeli.core.network.executor

import com.alcalist.tecmeli.core.network.AppError
import com.alcalist.tecmeli.core.network.handler.HttpResponseHandler
import com.alcalist.tecmeli.core.network.logger.NetworkLogger
import com.alcalist.tecmeli.core.network.mapper.ErrorMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Ejecutor de llamadas de red seguras.
 * Responsabilidad única: orquestar la ejecución de llamadas de red con manejo de excepciones.
 */
class SafeApiCallExecutor(
    private val responseHandler: HttpResponseHandler,
    private val errorMapper: ErrorMapper,
    private val logger: NetworkLogger
) {
    suspend fun <T, R> execute(
        call: suspend () -> Response<T>,
        transform: (T) -> R
    ): Result<R> = withContext(Dispatchers.IO) {
        try {
            val response = call()
            val result = responseHandler.handleResponse(response, transform)

            if (result.isFailure && response.isSuccessful.not()) {
                val error = result.exceptionOrNull()
                if (error is AppError.Server) {
                    logger.logServerError(error.code, error.message)
                }
            }

            result
        } catch (e: Exception) {
            val appError = errorMapper.mapException(e)
            logger.logUnexpectedError(appError.javaClass.simpleName, e)
            Result.failure(appError)
        }
    }
}

