package com.alcalist.tecmeli.core.network.logger

/**
 * Define un contrato para el registro y monitoreo de eventos de red.
 *
 * Esta interfaz facilita la implementación de diversos sistemas de logging (como Firebase Crashlytics,
 * Logcat, o un servicio propio de telemetría), permitiendo que la capa de red permanezca agnóstica
 * al destino final de los registros.
 */
interface NetworkLogger {
    /**
     * Registra un error que proviene de una respuesta fallida del servidor.
     *
     * @param code Código HTTP de error.
     * @param message Mensaje descriptivo del error.
     */
    fun logServerError(code: Int, message: String?)

    /**
     * Registra un error inesperado durante el procesamiento de una solicitud.
     *
     * @param errorType Una cadena identificadora del tipo de error (ej: el nombre de la excepción).
     * @param exception La excepción original capturada.
     */
    fun logUnexpectedError(errorType: String, exception: Exception)
}
