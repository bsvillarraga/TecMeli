package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ShortDescriptionDto(
    @SerializedName("type") var type: String? = null,
    @SerializedName("content") var content: String? = null
)
