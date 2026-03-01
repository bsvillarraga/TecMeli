package com.alcalist.tecmeli.domain.usecase

import com.alcalist.tecmeli.domain.model.ProductDetail
import com.alcalist.tecmeli.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: String): Result<ProductDetail> {
        return repository.getProductDetail(id)
    }
}
