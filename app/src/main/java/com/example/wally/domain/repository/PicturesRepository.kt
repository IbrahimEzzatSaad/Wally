package com.example.wally.domain.repository

import com.example.wally.data.api.model.PicturesItem
import kotlinx.coroutines.flow.Flow

interface PicturesRepository {

    suspend fun requestPictures(page: String): List<PicturesItem>

    suspend fun storePictures(pictures: List<PicturesItem>)

    fun getPictures(): Flow<List<PicturesItem>>

}