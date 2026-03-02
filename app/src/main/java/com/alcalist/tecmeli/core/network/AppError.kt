package com.alcalist.tecmeli.core.network

/**
 * Jerarquía de errores de la aplicación para desacoplar las excepciones 
 * técnicas de la lógica de negocio y UI.
 */
sealed class AppError : Exception() {
    class Network : AppError()
    class Timeout : AppError()
    data class Server(val code: Int, val msg: String) : AppError()
    data class Unknown(val throwable: Throwable) : AppError()
}
