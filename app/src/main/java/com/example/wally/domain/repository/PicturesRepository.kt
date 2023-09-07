package com.example.wally.domain.repository

import androidx.paging.PagingData
import com.example.wally.data.api.model.PicturesItem
import kotlinx.coroutines.flow.Flow

interface PicturesRepository {

    suspend fun requestPictures(): Flow<PagingData<PicturesItem>>

    suspend fun requestFeatured(): List<PicturesItem>

    suspend fun storePictures(pictures: List<PicturesItem>)

    suspend fun storeFeatured(pictures: List<PicturesItem>)

    suspend fun getFeatured(): Flow<List<PicturesItem>>


}