package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Objeto de Transferencia de Datos (DTO) que representa la información de paginación de la API.
 *
 * @property total Cantidad total de resultados disponibles para la búsqueda.
 * @property limit Límite de resultados solicitados en la petición actual.
 * @property offset Índice del primer resultado retornado.
 * @property last Enlace o identificador opcional del último registro.
 */
data class PagingDto(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("offset") var offset: Int? = null,
    @SerializedName("last") var last: String? = null
)
