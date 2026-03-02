package com.alcalist.tecmeli.domain.repository

import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.model.ProductDetail

/**
 * Contrato que define las operaciones de obtención de datos para productos de Mercado Libre.
 *
 * Esta interfaz reside en la capa de dominio, siguiendo los principios de Clean Architecture.
 * Permite que los casos de uso (Use Cases) interactúen con los datos sin conocer el origen
 * de los mismos (API, Base de Datos local, etc.).
 */
interface ProductRepository {
    /**
     * Realiza una búsqueda de productos basada en un término de consulta.
     *
     * @param query El texto a buscar entre los productos activos.
     * @return [Result] con una lista de modelos [Product] en caso de éxito, o un fallo mapeado.
     */
    suspend fun searchProducts(query: String): Result<List<Product>>

    /**
     * Obtiene el detalle completo de un producto específico mediante su identificador.
     *
     * @param id El identificador único del producto (ej: "MCO12345").
     * @return [Result] con el modelo [ProductDetail] si se encuentra, o un fallo en caso contrario.
     */
    suspend fun getProductDetail(id: String): Result<ProductDetail>
}
