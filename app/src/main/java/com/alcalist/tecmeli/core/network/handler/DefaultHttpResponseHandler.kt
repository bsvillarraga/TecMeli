package com.alcalist.tecmeli.core.network.handler

import com.alcalist.tecmeli.core.network.AppError
import retrofit2.Response

/**
 * Implementación por defecto del HttpResponseHandler.
 */
class DefaultHttpResponseHandler : HttpResponseHandler {
    override fun <T, R> handleResponse(
        response: Response<T>,
        transform: (T) -> R
    ): Result<R> = when {
        response.isSuccessful -> {
            val body = response.body()
            if (body != null) {
                Result.success(transform(body))
            } else {
                Result.failure(
                    AppError.Server(response.code(), "Cuerpo de respuesta vacío")
                )
            }
        }
        else -> {
            Result.failure(
                AppError.Server(response.code(), response.message())
            )
        }
    }
}

