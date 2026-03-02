package com.alcalist.tecmeli.core.network

import com.alcalist.tecmeli.core.network.executor.SafeApiCallExecutor
import com.alcalist.tecmeli.core.network.handler.DefaultHttpResponseHandler
import com.alcalist.tecmeli.core.network.logger.DefaultNetworkLogger
import com.alcalist.tecmeli.core.network.mapper.DefaultErrorMapper
import retrofit2.Response

/**
 * Función de utilidad de alto nivel para ejecutar llamadas de red de forma segura.
 *
 * Provee un punto de entrada simplificado para realizar peticiones API, instanciando
 * las dependencias por defecto del motor de ejecución de red ([SafeApiCallExecutor]).
 *
 * **Nota:** En una arquitectura más avanzada, estas dependencias se inyectarían vía Hilt/Dagger,
 * pero esta extensión permite mantener la simplicidad y compatibilidad en el repositorio.
 *
 * @param T Tipo del DTO (Data Transfer Object) esperado en la respuesta.
 * @param R Tipo del modelo de dominio al cual se transformará la respuesta.
 * @param call Lambda suspendida que ejecuta la petición a Retrofit.
 * @param transform Lambda que define cómo convertir el DTO [T] al modelo [R].
 * @return [Result] con el objeto transformado o un error mapeado.
 */
suspend fun <T, R> safeApiCall(
    call: suspend () -> Response<T>,
    transform: (T) -> R
): Result<R> {
    val executor = SafeApiCallExecutor(
        responseHandler = DefaultHttpResponseHandler(),
        errorMapper = DefaultErrorMapper(),
        logger = DefaultNetworkLogger()
    )
    return executor.execute(call, transform)
}
