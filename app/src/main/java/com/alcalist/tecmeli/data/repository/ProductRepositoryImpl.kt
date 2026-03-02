package com.alcalist.tecmeli.data.repository

import com.alcalist.tecmeli.core.network.safeApiCall
import com.alcalist.tecmeli.data.mapper.toDetailDomain
import com.alcalist.tecmeli.data.mapper.toDomain
import com.alcalist.tecmeli.data.remote.api.MeliApi
import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.model.ProductDetail
import com.alcalist.tecmeli.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * Implementación de [ProductRepository] que utiliza la API de Mercado Libre como fuente de datos.
 *
 * Esta clase actúa como el mediador entre la capa de red (MeliApi) y la capa de dominio.
 * Utiliza [safeApiCall] para manejar de forma segura las peticiones HTTP y transformar
 * los DTOs en modelos de dominio.
 *
 * @property meliApi Interfaz de Retrofit que define los endpoints de productos.
 */
class ProductRepositoryImpl @Inject constructor(
    private val meliApi: MeliApi
) : ProductRepository {

    /**
     * Busca productos consumiendo el endpoint de búsqueda de Mercado Libre.
     *
     * @param query Término de búsqueda provisto por el usuario.
     * @return [Result] con una lista de [Product].
     */
    override suspend fun searchProducts(query: String): Result<List<Product>> = safeApiCall(
        call = { meliApi.searchProducts(query = query) },
        transform = { body -> body.results.map { it.toDomain() } }
    )

    /**
     * Obtiene el detalle de un producto específico.
     *
     * @param id Identificador único del producto.
     * @return [Result] con el [ProductDetail]. Lanza una excepción si el producto no se encuentra en los resultados.
     */
    override suspend fun getProductDetail(id: String): Result<ProductDetail> = safeApiCall(
        call = { meliApi.getProductById(productId = id) },
        transform = { body -> 
            val product = body.results.firstOrNull() 
            product?.toDetailDomain() ?: throw Exception("Product not found")
        }
    )
}
