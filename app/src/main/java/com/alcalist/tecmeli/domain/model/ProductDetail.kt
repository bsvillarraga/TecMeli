package com.alcalist.tecmeli.domain.model

data class ProductDetail(
    val id: String,
    val title: String,
    val pictures: List<String> = emptyList(),
    val attributes: List<ProductAttribute> = emptyList(),
    val description: String? = null
)
