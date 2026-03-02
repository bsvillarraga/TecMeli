package com.alcalist.tecmeli.domain.usecase

import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * Caso de uso encargado de gestionar la búsqueda de productos.
 *
 * Contiene la lógica de negocio asociada a la búsqueda, como la validación de que el término
 * de búsqueda no esté vacío antes de realizar la petición al repositorio.
 *
 * @property repository Repositorio de productos para acceder a la fuente de datos.
 */
class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    /**
     * Ejecuta la búsqueda de productos.
     *
     * @param query Término de búsqueda.
     * @return [Result] con una lista de [Product]. Si la búsqueda es vacía, retorna una lista vacía exitosa.
     */
    suspend operator fun invoke(query: String): Result<List<Product>> {
        return if (query.isBlank()) {
            Result.success(emptyList())
        } else {
            repository.searchProducts(query)
        }
    }
}
