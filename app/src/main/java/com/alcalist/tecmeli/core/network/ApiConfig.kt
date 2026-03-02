package com.alcalist.tecmeli.core.network

/**
 * Encapsula los parámetros de configuración necesarios para la autenticación con la API.
 *
 * Esta clase centraliza las credenciales requeridas por el flujo OAuth 2.0 de Mercado Libre,
 * permitiendo que sean inyectadas de forma consistente en los repositorios de tokens
 * y otros componentes de red.
 *
 * @property clientId Identificador único de la aplicación registrado en el portal de desarrolladores.
 * @property clientSecret Clave secreta de la aplicación utilizada para validar la identidad durante el refresh.
 * @property refreshToken Token de larga duración utilizado para obtener nuevos Access Tokens cuando expiran.
 */
data class ApiConfig(
    val clientId: String,
    val clientSecret: String,
    val refreshToken: String
)
