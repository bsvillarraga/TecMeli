package com.alcalist.tecmeli.core.network.mapper

import com.alcalist.tecmeli.core.network.AppError
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Implementación por defecto del ErrorMapper.
 */
class DefaultErrorMapper : ErrorMapper {
    override fun mapException(exception: Exception): AppError = when (exception) {
        is SocketTimeoutException -> AppError.Timeout()
        is IOException -> AppError.Network()
        else -> AppError.Unknown(exception)
    }
}

