package com.alcalist.tecmeli.domain.model

/**
 * Modelo de dominio que contiene la información detallada de un producto.
 *
 * Utilizado para la vista de detalle de producto.
 *
 * @property id Identificador único del producto.
 * @property title Nombre completo o título del producto.
 * @property pictures Lista de URLs de imágenes del producto.
 * @property attributes Lista completa de atributos y especificaciones técnicas.
 * @property description Descripción textual del producto.
 */
data class ProductDetail(
    val id: String,
    val title: String,
    val pictures: List<String> = emptyList(),
    val attributes: List<ProductAttribute> = emptyList(),
    val description: String? = null
)
