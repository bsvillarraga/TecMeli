package com.alcalist.tecmeli.core.network.handler

import com.alcalist.tecmeli.core.network.AppError
import retrofit2.Response

/**
 * Implementación estándar de [HttpResponseHandler].
 *
 * Esta clase se encarga de gestionar el flujo de éxito o error de una respuesta HTTP de Retrofit.
 * Valida que la respuesta sea exitosa (código 2xx), que el cuerpo no sea nulo, y delega
 * la transformación de los datos a una función lambda provista.
 */
class DefaultHttpResponseHandler : HttpResponseHandler {

    /**
     * Procesa la respuesta de red y la encapsula en un [Result].
     *
     * @param T El tipo del DTO (Data Transfer Object) recibido.
     * @param R El tipo del modelo de dominio resultante.
     * @param response La instancia de [Response] devuelta por Retrofit.
     * @param transform La función de mapeo de DTO a Dominio.
     * @return [Result.success] con el objeto transformado si todo es correcto.
     * @return [Result.failure] con [AppError.Server] si la respuesta es fallida o el cuerpo es nulo.
     */
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
