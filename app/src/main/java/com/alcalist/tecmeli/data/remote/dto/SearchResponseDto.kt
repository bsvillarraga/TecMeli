package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Objeto de Transferencia de Datos (DTO) que representa la respuesta raíz de una búsqueda en Mercado Libre.
 *
 * Encapsula la lista de resultados encontrados y la información necesaria para gestionar
 * la paginación de los mismos.
 *
 * @property paging Información sobre la paginación de la respuesta (total, offset, etc.).
 * @property results Lista de productos ([ResultsDto]) que coinciden con los criterios de búsqueda.
 */
data class SearchResponseDto(
    @SerializedName("paging") var paging: PagingDto? = PagingDto(),
    @SerializedName("results") var results: ArrayList<ResultsDto> = arrayListOf()
)
