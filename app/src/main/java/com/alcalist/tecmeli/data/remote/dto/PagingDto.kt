package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PagingDto(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("offset") var offset: Int? = null,
    @SerializedName("last") var last: String? = null
)