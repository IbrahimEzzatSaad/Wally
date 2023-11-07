package com.example.wally.domain.repository

import androidx.paging.PagingData
import com.example.wally.data.api.model.PicturesItem
import kotlinx.coroutines.flow.Flow

interface PicturesRepository {

    suspend fun requestPictures(): Flow<PagingData<PicturesItem>>

    suspend fun requestFeatured(): List<PicturesItem>

    suspend fun storeFeatured(pictures: List<PicturesItem>)

    suspend fun getFeatured(): Flow<List<PicturesItem>>

    suspend fun getFavorite(): List<PicturesItem>

    suspend fun getCategoryList(id: String): Flow<PagingData<PicturesItem>>

    suspend fun getSearch(query: String): Flow<PagingData<PicturesItem>>

    suspend fun updateFavorite(item: PicturesItem)


}