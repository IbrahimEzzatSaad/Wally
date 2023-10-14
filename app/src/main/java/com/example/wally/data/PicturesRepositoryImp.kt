package com.example.wally.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.wally.data.api.ApiParameters.PAGE_SIZE
import com.example.wally.data.api.model.ApiPictures
import com.example.wally.domain.repository.PicturesRepository
import com.example.wally.domain.usecases.NetworkException
import com.example.wally.data.api.PicturesApi
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.data.cache.Cache
import com.example.wally.data.cache.model.CachedFavoritePicture
import com.example.wally.data.cache.model.CachedFeaturedPicture
import com.example.wally.data.paging.PicturesRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class PicturesRepositoryImp @Inject constructor(
    private val cache: Cache,
    private val api: PicturesApi
) : PicturesRepository {


    @OptIn(ExperimentalPagingApi::class)
    override suspend fun requestPictures(): Flow<PagingData<PicturesItem>> {
        try {
            val pager = Pager(
                config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = true),
                remoteMediator = PicturesRemoteMediator(
                    cache, api
                )
            ) {
                cache.getPictures()
            }


            return pager.flow.map { data ->
                data.map { it.toDomain(it.favorite) }
            }
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun requestFeatured(): List<PicturesItem> {

        try {
            val results: ApiPictures = api.getFeatured()
            return results.toList()
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun storeFeatured(pictures: List<PicturesItem>) {
        cache.deleteFeatured()
        pictures.forEach { picture ->
            cache.insertFeatured(CachedFeaturedPicture.fromDomain(picture))
        }
    }

    override suspend fun getFeatured(): Flow<List<PicturesItem>> {
        return cache.getFeatured()
            .distinctUntilChanged() // ensures only events with new information get to the subscriber.
            .map { picturesList ->
                picturesList.map { it.toDomain() }
            }
    }

    override suspend fun getFavorite(): List<PicturesItem> {
        return cache.getFavorite()
            .map {
                 it.toDomain(true)
            }
    }

    override suspend fun updateFavorite(id: String) {
        if(cache.getFavoriteById(id) == null){
            val picture = cache.getPictureById(id)!!.toDomain()
            cache.insertFavorite(CachedFavoritePicture.fromDomain(picture))
            cache.updatePictureToFavorite(id)
        }else
            cache.deleteFavorite(id)
    }
}