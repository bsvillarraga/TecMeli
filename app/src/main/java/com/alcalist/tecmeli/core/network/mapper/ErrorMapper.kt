package com.alcalist.tecmeli.core.network.mapper

import com.alcalist.tecmeli.core.network.AppError
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Interfaz para mapear excepciones a errores de aplicación.
 * Responsabilidad: convertir excepciones técnicas a errores de dominio.
 */
interface ErrorMapper {
    fun mapException(exception: Exception): AppError
}

