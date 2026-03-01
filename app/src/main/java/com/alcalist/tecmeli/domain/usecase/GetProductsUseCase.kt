package com.alcalist.tecmeli.domain.usecase

import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(query: String): Result<List<Product>> {
        return if (query.isBlank()) {
            Result.success(emptyList())
        } else {
            repository.searchProducts(query)
        }
    }
}
