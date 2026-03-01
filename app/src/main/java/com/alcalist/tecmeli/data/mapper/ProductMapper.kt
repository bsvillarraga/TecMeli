package com.alcalist.tecmeli.data.mapper

import com.alcalist.tecmeli.data.remote.dto.AttributesDto
import com.alcalist.tecmeli.data.remote.dto.ResultsDto
import com.alcalist.tecmeli.domain.model.Product
import com.alcalist.tecmeli.domain.model.ProductAttribute
import com.alcalist.tecmeli.domain.model.ProductDetail

fun ResultsDto.toDomain(): Product {
    return Product(
        id = this.id,
        title = this.name,
        domainId = this.domainId,
        lastUpdated = this.lastUpdated,
        thumbnail = this.pictures.firstOrNull()?.url
    )
}

fun ResultsDto.toDetailDomain(): ProductDetail {
    return ProductDetail(
        id = this.id,
        title = this.name,
        pictures = this.pictures.map { it.url },
        attributes = this.attributes.map { it.toDomain() },
        description = this.shortDescription?.content
    )
}

fun AttributesDto.toDomain(): ProductAttribute {
    return ProductAttribute(
        id = this.id,
        name = this.name,
        value = this.valueName
    )
}
