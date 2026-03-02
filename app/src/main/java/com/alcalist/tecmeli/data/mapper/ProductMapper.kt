package com.alcalist.tecmeli.data.mapper

import com.alcalist.tecmeli.data.remote.dto.AttributesDto
import com.alcalist.tecmeli.data.remote.dto.ResultsDto
import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.model.ProductAttribute
import com.alcalist.tecmeli.domain.model.ProductDetail

/**
 * Funciones de extensión para mapear objetos de transferencia de datos (DTO) a modelos de dominio.
 *
 * El mapeo es esencial para asegurar que la capa de dominio sea independiente de los nombres
 * de campos y estructuras definidas por la API externa (Mercado Libre).
 */

/**
 * Convierte un [ResultsDto] de la API en el modelo simplificado [Product].
 *
 * @receiver El objeto DTO retornado por el endpoint de búsqueda.
 * @return Una instancia de [Product] lista para ser mostrada en listas.
 */
fun ResultsDto.toDomain(): Product {
    return Product(
        id = this.id,
        title = this.name,
        domainId = this.domainId,
        lastUpdated = this.lastUpdated,
        thumbnail = this.pictures.firstOrNull()?.url
    )
}

/**
 * Convierte un [ResultsDto] en el modelo detallado [ProductDetail].
 *
 * @receiver El objeto DTO con la información completa del producto.
 * @return Una instancia de [ProductDetail] con atributos y galería de imágenes.
 */
fun ResultsDto.toDetailDomain(): ProductDetail {
    return ProductDetail(
        id = this.id,
        title = this.name,
        pictures = this.pictures.map { it.url },
        attributes = this.attributes.map { it.toDomain() },
        description = this.shortDescription?.content
    )
}

/**
 * Convierte un [AttributesDto] en el modelo de dominio [ProductAttribute].
 *
 * @receiver El atributo técnico proveniente del DTO.
 * @return Una representación simplificada del atributo (Nombre: Valor).
 */
fun AttributesDto.toDomain(): ProductAttribute {
    return ProductAttribute(
        id = this.id,
        name = this.name,
        value = this.valueName
    )
}
