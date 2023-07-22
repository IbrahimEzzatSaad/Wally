package com.example.wally.data.cache

import com.example.wally.data.cache.model.CachedPicture
import kotlinx.coroutines.flow.Flow

interface Cache {

    fun getPictures(): Flow<List<CachedPicture>>

    fun getPicture(id: String): Flow<CachedPicture>

    suspend fun storePictures(vararg pictures: CachedPicture)

    fun deleteAll()


}