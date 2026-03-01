package com.alcalist.tecmeli.domain.model

data class Product(
    val id: String,
    val title: String,
    val thumbnail: String? = null,
    val domainId: String? = null,
    val lastUpdated: String? = null,
    val attributes: List<ProductAttribute> = emptyList()
)
