package com.alcalist.tecmeli.data.repository

import com.alcalist.tecmeli.core.network.safeApiCall
import com.alcalist.tecmeli.data.mapper.toDetailDomain
import com.alcalist.tecmeli.data.mapper.toDomain
import com.alcalist.tecmeli.data.remote.api.MeliApi
import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.model.ProductDetail
import com.alcalist.tecmeli.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val meliApi: MeliApi
) : ProductRepository {
    override suspend fun searchProducts(query: String): Result<List<Product>> = safeApiCall(
        call = { meliApi.searchProducts(query = query) },
        transform = { body -> body.results.map { it.toDomain() } }
    )

    override suspend fun getProductDetail(id: String): Result<ProductDetail> = safeApiCall(
        call = { meliApi.getProductById(productId = id) },
        transform = { body -> 
            val product = body.results.firstOrNull() 
            product?.toDetailDomain() ?: throw Exception("Product not found")
        }
    )
}
