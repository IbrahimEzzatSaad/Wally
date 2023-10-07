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
import com.example.wally.data.cache.model.CachedPicture
import com.example.wally.data.cache.model.toDomain
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
                data.map { it.toDomain() }
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
        pictures.onEach { it.featured = true }
        pictures.forEach { picture ->
            Log.i("Featured_Item:", picture.toString())
            cache.storePictures(CachedPicture.fromDomain(picture))
        }
    }

    override suspend fun storePictures(pictures: List<PicturesItem>) {
        cache.deletePictures()
        pictures.forEach { picture ->
            Log.i("ListInformation:", picture.toString())
            cache.storePictures(CachedPicture.fromDomain(picture))
        }
    }

    override suspend fun getFeatured(): Flow<List<PicturesItem>> {
        return cache.getFeatured()
            .distinctUntilChanged() // ensures only events with new information get to the subscriber.
            .map { picturesList ->
                picturesList.map { it.toDomain() }
            }
    }
}