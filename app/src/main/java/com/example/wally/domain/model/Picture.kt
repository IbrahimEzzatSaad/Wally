package com.example.wally.domain.model

import com.example.wally.data.api.model.Urls

data class Pic(
    val altDescription: String,
    val color: String,
    val description: String,
    val height: Int,
    val id: String,
    val likes: Int,
    val urls: Urls,
    val width: Int
)