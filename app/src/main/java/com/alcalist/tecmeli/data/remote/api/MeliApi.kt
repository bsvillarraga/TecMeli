package com.alcalist.tecmeli.data.remote.api

import com.alcalist.tecmeli.data.remote.dto.SearchResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MeliApi {
    @GET("products/search")
    suspend fun searchProducts(
        @Query("status") status: String = "active",
        @Query("site_id") siteId: String = "MCO",
        @Query("q") query: String
    ): Response<SearchResponseDto>

    @GET("products/search")
    suspend fun getProductById(
        @Query("site_id") siteId: String = "MCO",
        @Query("product_identifier") productId: String
    ): Response<SearchResponseDto>
}
