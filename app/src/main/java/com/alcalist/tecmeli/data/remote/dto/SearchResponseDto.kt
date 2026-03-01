package com.alcalist.tecmeli.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("paging") var paging: PagingDto? = PagingDto(),
    @SerializedName("results") var results: ArrayList<ResultsDto> = arrayListOf()
)