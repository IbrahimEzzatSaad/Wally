package com.example.wally.data.cache

import androidx.paging.PagingSource
import com.example.wally.data.cache.model.CachedFavoritePicture
import com.example.wally.data.cache.model.CachedFeaturedPicture
import com.example.wally.data.cache.model.CachedPicture
import com.example.wally.data.cache.model.PageKeyEntity
import kotlinx.coroutines.flow.Flow

interface Cache {

    fun getPictures(): PagingSource<Int, CachedPicture>

    suspend fun insertPictures(vararg pictures: CachedPicture)

    fun deletePictures()

    suspend fun getPictureById(id : String) : CachedPicture?

    fun getItemsCount() : Int

    suspend fun updatePictureToFavorite(id : String)


    fun getFeatured(): Flow<List<CachedFeaturedPicture>>

    suspend fun insertFeatured(vararg pictures: CachedFeaturedPicture)

    fun deleteFeatured()


    suspend fun getFavorite(): Flow<List<CachedFavoritePicture>>

    suspend fun insertFavorite(picture: CachedFavoritePicture)

    suspend fun deleteFavorite(id : String)

    suspend fun getFavoriteById(id: String) : CachedFavoritePicture?


    suspend fun insertPageKey(pageKeyEntity: PageKeyEntity)

    fun getNexPage() : Long

    fun clearPageKeys()

}