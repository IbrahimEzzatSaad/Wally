package com.example.wally.data.cache

import androidx.paging.PagingSource
import com.example.wally.data.cache.model.CachedPicture
import kotlinx.coroutines.flow.Flow

interface Cache {

    fun getPictures(): PagingSource<Int, CachedPicture>

    fun getFeatured(): Flow<List<CachedPicture>>

    suspend fun storePictures(vararg pictures: CachedPicture)
    fun getPictureById(id : String) : CachedPicture?

    fun deletePictures()

    fun deleteFeatured()

    fun getItemsCount() : Int


}