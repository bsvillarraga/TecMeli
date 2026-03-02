package com.alcalist.tecmeli.core.network

/**
 * Jerarquía de errores de la aplicación.
 *
 * Esta clase sella (sealed class) define los posibles estados de error que la aplicación
 * puede manejar. Su objetivo es desacoplar las excepciones técnicas (como IOException o HttpException)
 * de la lógica de negocio y la interfaz de usuario, permitiendo un manejo de errores más semántico.
 */
sealed class AppError : Exception() {
    /** Error de conectividad de red (sin internet o problemas de conexión). */
    class Network : AppError()

    /** Tiempo de espera agotado al conectar con el servidor. */
    class Timeout : AppError()

    /**
     * Error retornado por el servidor (4xx, 5xx).
     * @property code Código HTTP de la respuesta.
     * @property msg Mensaje de error retornado por el servidor o descripción técnica.
     */
    data class Server(val code: Int, val msg: String) : AppError()

    /**
     * Error no clasificado o excepción inesperada durante la ejecución.
     * @property throwable La causa original del error para propósitos de depuración.
     */
    data class Unknown(val throwable: Throwable) : AppError()
}
