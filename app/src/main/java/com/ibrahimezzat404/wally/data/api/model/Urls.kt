package com.ibrahimezzat404.wally.data.api.model


import com.google.gson.annotations.SerializedName

data class Urls(
    @SerializedName("full")
    val full: String,
    @SerializedName("raw")
    val raw: String,
    @SerializedName("regular")
    val regular: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("small_s3")
    val smallS3: String,
    @SerializedName("thumb")
    val thumb: String
)

data class Links(
    @SerializedName("download_location")
    val downloadLink: String,
)


data class Artist(
    @SerializedName("username")
    val username: String,
    @SerializedName("name")
    val name: String,
)