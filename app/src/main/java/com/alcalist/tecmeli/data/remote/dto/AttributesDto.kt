package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Objeto de Transferencia de Datos (DTO) que representa un atributo técnico de un producto.
 *
 * Los atributos son pares clave-valor que describen especificaciones del producto,
 * como color, marca, modelo, material, etc.
 *
 * @property id Identificador único del atributo (ej: "COLOR", "BRAND").
 * @property name Nombre legible del atributo (ej: "Color", "Marca").
 * @property valueName Valor legible asociado al atributo (ej: "Rojo", "Samsung").
 */
data class AttributesDto(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("value_name") var valueName: String? = null
)
