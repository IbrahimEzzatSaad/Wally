package com.example.wally.domain.repository

import androidx.paging.PagingData
import com.example.wally.data.api.model.PictureModel
import kotlinx.coroutines.flow.Flow

interface PicturesRepository {

    suspend fun requestPictures(): Flow<PagingData<PictureModel>>

    suspend fun requestFeatured(): List<PictureModel>

    suspend fun storeFeatured(pictures: List<PictureModel>)

    suspend fun getFeatured(): Flow<List<PictureModel>>

    suspend fun getFavorite(): Flow<List<PictureModel>>

    suspend fun getCategoryList(id: String): Flow<PagingData<PictureModel>>

    suspend fun getSearch(query: String): Flow<PagingData<PictureModel>>

    suspend fun updateFavorite(item: PictureModel)


}