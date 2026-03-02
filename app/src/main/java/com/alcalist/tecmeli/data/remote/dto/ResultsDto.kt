package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Objeto de Transferencia de Datos (DTO) que representa un producto en la respuesta de Mercado Libre.
 *
 * Contiene tanto la información básica para listados como la detallada para la vista de producto,
 * permitiendo una gestión unificada de la respuesta del endpoint de búsqueda e identificación.
 *
 * @property id Identificador único del producto.
 * @property catalogProductId Identificador del producto en el catálogo global.
 * @property domainId Identificador de la categoría o dominio del producto.
 * @property name Nombre o título descriptivo del producto.
 * @property attributes Lista de especificaciones técnicas (ej: color, modelo).
 * @property shortDescription Breve descripción o contenido textual del producto.
 * @property pictures Galería de imágenes asociadas al producto.
 * @property lastUpdated Fecha de la última actualización del registro en el servidor.
 */
data class ResultsDto(
    @SerializedName("id") var id: String,
    @SerializedName("catalog_product_id") var catalogProductId: String,
    @SerializedName("domain_id") var domainId: String,
    @SerializedName("name") var name: String,
    @SerializedName("attributes") var attributes: ArrayList<AttributesDto> = arrayListOf(),
    @SerializedName("short_description") var shortDescription: ShortDescriptionDto? = ShortDescriptionDto(),
    @SerializedName("pictures") var pictures: ArrayList<PicturesDto> = arrayListOf(),
    @SerializedName("last_updated") var lastUpdated: String? = null
)
