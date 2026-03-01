package com.alcalist.tecmeli.domain.repository

import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.model.ProductDetail

interface ProductRepository {
    suspend fun searchProducts(query: String): Result<List<Product>>
    suspend fun getProductDetail(id: String): Result<ProductDetail>
}
