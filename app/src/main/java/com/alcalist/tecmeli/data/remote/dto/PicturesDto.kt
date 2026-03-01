package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PicturesDto(
    @SerializedName("id") var id: String,
    @SerializedName("url") var url: String
)
