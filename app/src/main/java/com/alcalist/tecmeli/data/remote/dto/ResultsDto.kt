package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResultsDto(
    @SerializedName("id") var id: String,
    @SerializedName("catalog_product_id") var catalogProductId: String,
    @SerializedName("domain_id") var domainId: String,
    @SerializedName("name") var name: String,
    @SerializedName("attributes") var attributes: ArrayList<AttributesDto> = arrayListOf(),
    @SerializedName("short_description") var shortDescription: ShortDescriptionDto? = ShortDescriptionDto(),
    @SerializedName("pictures") var pictures: ArrayList<PicturesDto> = arrayListOf(),
    @SerializedName("last_updated") var lastUpdated: String? = null
)