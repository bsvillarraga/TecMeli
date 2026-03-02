package com.alcalist.tecmeli.domain.model

/**
 * Modelo de dominio que representa un producto simplificado.
 *
 * Utilizado principalmente para mostrar información básica en listados de búsqueda.
 *
 * @property id Identificador único del producto.
 * @property title Nombre o título del producto.
 * @property thumbnail URL de la imagen en miniatura.
 * @property domainId Identificador de la categoría o dominio.
 * @property lastUpdated Fecha de la última actualización del producto.
 * @property attributes Lista de atributos clave del producto.
 */
data class Product(
    val id: String,
    val title: String,
    val thumbnail: String? = null,
    val domainId: String? = null,
    val lastUpdated: String? = null,
    val attributes: List<ProductAttribute> = emptyList()
)
