package com.alcalist.tecmeli.data.remote.api

import com.alcalist.tecmeli.data.remote.dto.SearchResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz de Retrofit que define los endpoints para interactuar con el catálogo de productos de Mercado Libre.
 */
interface MeliApi {
    /**
     * Busca productos según los criterios especificados.
     *
     * @param status Filtra por el estado del producto (por defecto "active").
     * @param siteId Identificador del sitio geográfico (por defecto "MCO" para Colombia).
     * @param query Término de búsqueda (q).
     * @return [Response] que contiene un [SearchResponseDto] con los resultados de la búsqueda.
     */
    @GET("products/search")
    suspend fun searchProducts(
        @Query("status") status: String = "active",
        @Query("site_id") siteId: String = "MCO",
        @Query("q") query: String
    ): Response<SearchResponseDto>

    /**
     * Obtiene la información detallada de un producto mediante su identificador único.
     *
     * @param siteId Identificador del sitio geográfico.
     * @param productId Identificador del producto (ej: "MCO12345").
     * @return [Response] con un [SearchResponseDto] donde se espera el producto en la lista de resultados.
     */
    @GET("products/search")
    suspend fun getProductById(
        @Query("site_id") siteId: String = "MCO",
        @Query("product_identifier") productId: String
    ): Response<SearchResponseDto>
}
