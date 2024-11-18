package com.ibrahimezzat404.wally.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiSearch(
    @SerializedName("total")
    val total: Long,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("results")
    val pictures : ApiPictures

)