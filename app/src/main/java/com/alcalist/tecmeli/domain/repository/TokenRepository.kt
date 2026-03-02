package com.alcalist.tecmeli.domain.repository

/**
 * Contrato para la gestión de tokens de autenticación.
 *
 * Define las operaciones necesarias para obtener y renovar el token de acceso
 * requerido para realizar peticiones autorizadas a la API de Mercado Libre.
 */
interface TokenRepository {
    /**
     * Obtiene el token de acceso actual almacenado.
     *
     * @return El token de acceso como [String], o null si no hay un token disponible.
     */
    fun getAccessToken(): String?

    /**
     * Solicita la renovación del token de acceso actual.
     *
     * @return un [Result] que contiene el nuevo token de acceso si la operación fue exitosa,
     * o una excepción en caso de error.
     */
    suspend fun refreshToken(): Result<String>
}
