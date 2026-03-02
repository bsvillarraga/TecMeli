package com.alcalist.tecmeli.core.network.executor

import com.alcalist.tecmeli.core.network.AppError
import com.alcalist.tecmeli.core.network.handler.HttpResponseHandler
import com.alcalist.tecmeli.core.network.logger.NetworkLogger
import com.alcalist.tecmeli.core.network.mapper.ErrorMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Orquestador de llamadas de red seguras.
 *
 * Esta clase es el núcleo del manejo de peticiones API. Su responsabilidad única (SRP) es
 * ejecutar llamadas suspendidas, gestionar las excepciones mediante [ErrorMapper], procesar la respuesta
 * con [HttpResponseHandler] y notificar eventos importantes a través de [NetworkLogger].
 *
 * Utiliza [Dispatchers.IO] para asegurar que las operaciones de red no bloqueen el hilo principal.
 *
 * @property responseHandler Manejador para validar y transformar respuestas HTTP.
 * @property errorMapper Mapeador para convertir excepciones en errores de aplicación.
 * @property logger Sistema para registrar fallos técnicos y de servidor.
 */
class SafeApiCallExecutor(
    private val responseHandler: HttpResponseHandler,
    private val errorMapper: ErrorMapper,
    private val logger: NetworkLogger
) {
    /**
     * Ejecuta una llamada de red de forma segura dentro de un bloque try-catch controlado.
     *
     * @param T El tipo de dato del cuerpo de la respuesta de Retrofit.
     * @param R El tipo de dato de dominio deseado tras la transformación.
     * @param call Lambda suspendida que representa la llamada a la interfaz de Retrofit.
     * @param transform Lambda para mapear de [T] a [R].
     * @return [Result] que encapsula el éxito con [R] o el fallo con [AppError].
     */
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
