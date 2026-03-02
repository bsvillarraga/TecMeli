package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Objeto de Transferencia de Datos (DTO) que representa una descripción corta del producto.
 *
 * Este objeto encapsula el contenido textual descriptivo que Mercado Libre asocia
 * a los resultados de búsqueda o detalle.
 *
 * @property type El tipo de descripción o formato (ej: "text", "html").
 * @property content El contenido textual de la descripción.
 */
data class ShortDescriptionDto(
    @SerializedName("type") var type: String? = null,
    @SerializedName("content") var content: String? = null
)
