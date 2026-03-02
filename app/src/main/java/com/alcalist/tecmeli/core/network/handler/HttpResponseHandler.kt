package com.alcalist.tecmeli.core.network.handler

import retrofit2.Response

/**
 * Define un contrato para la gestión y validación de respuestas HTTP de Retrofit.
 *
 * El propósito de esta interfaz es encapsular la lógica de evaluación de éxito o fallo
 * de las respuestas del servidor, realizando la transformación de los datos de transporte (DTO)
 * a modelos útiles para la aplicación.
 */
interface HttpResponseHandler {
    /**
     * Evalúa una respuesta de Retrofit y retorna un [Result] con el dato transformado o una falla.
     *
     * @param T Tipo del cuerpo de la respuesta original (DTO).
     * @param R Tipo del resultado transformado deseado.
     * @param response La respuesta HTTP completa provista por Retrofit.
     * @param transform Función lambda para convertir de [T] a [R].
     * @return [Result] exitoso si la respuesta es 2xx y tiene cuerpo, o un [Result] fallido en otro caso.
     */
    fun <T, R> handleResponse(
        response: Response<T>,
        transform: (T) -> R
    ): Result<R>
}
