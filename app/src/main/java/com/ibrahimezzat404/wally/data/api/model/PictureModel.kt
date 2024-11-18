package com.ibrahimezzat404.wally.data.api.model


import com.google.gson.annotations.SerializedName


data class PictureModel (
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("color")
    val color: String,
    @SerializedName("user")
    val user: Artist,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("links")
    val links: Links,
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int,
    @SerializedName("favorite")
    val favorite: Boolean = false
)

