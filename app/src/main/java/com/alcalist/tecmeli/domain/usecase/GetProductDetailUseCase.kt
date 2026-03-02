package com.alcalist.tecmeli.domain.usecase

import com.alcalist.tecmeli.domain.model.ProductDetail
import com.alcalist.tecmeli.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * Caso de uso encargado de obtener la información detallada de un producto específico.
 *
 * @property repository Repositorio de productos para acceder a la fuente de datos.
 */
class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    /**
     * Ejecuta la consulta del detalle del producto.
     *
     * @param id Identificador único del producto.
     * @return [Result] que contiene el modelo de dominio [ProductDetail].
     */
    suspend operator fun invoke(id: String): Result<ProductDetail> {
        return repository.getProductDetail(id)
    }
}
