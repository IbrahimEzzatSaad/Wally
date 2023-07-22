package com.example.wally.data.api.model


import com.google.gson.annotations.SerializedName

data class PicturesItem(
    val idd: Int,
    @SerializedName("id")
    val code: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("color")
    val color: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int
)

