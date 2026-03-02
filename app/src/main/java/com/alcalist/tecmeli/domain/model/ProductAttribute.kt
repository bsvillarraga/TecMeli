package com.alcalist.tecmeli.domain.model

/**
 * Modelo de dominio que representa un atributo o especificación técnica de un producto.
 *
 * @property id Identificador del atributo (ej: "COLOR").
 * @property name Nombre del atributo (ej: "Color").
 * @property value Valor del atributo (ej: "Rojo").
 */
data class ProductAttribute(
    val id: String,
    val name: String,
    val value: String?
)
