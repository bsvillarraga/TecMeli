package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AttributesDto(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("value_name") var valueName: String? = null
)
