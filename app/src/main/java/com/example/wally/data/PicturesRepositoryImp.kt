package com.example.wally.data

import android.util.Log
import com.example.wally.data.api.model.ApiPictures
import com.example.wally.domain.repository.PicturesRepository
import com.example.wally.domain.usecases.NetworkException
import com.example.wally.data.api.PicturesApi
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.data.cache.Cache
import com.example.wally.data.cache.model.CachedPicture
import com.example.wally.data.cache.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class PicturesRepositoryImp @Inject constructor(
    private val cache: Cache,
    private val api: PicturesApi
) : PicturesRepository {

    override suspend fun requestPictures(page: String): List<PicturesItem> {

        try {
            val results: ApiPictures = api.getPictures(page)
            return results.toList()
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun storePictures(pictures: List<PicturesItem>) {
        cache.deleteAll()
        pictures.forEach { picture ->
            Log.i("ListInformation:", picture.toString())
            cache.storePictures(CachedPicture.fromDomain(picture))
        }
    }

    override fun getPictures(): Flow<List<PicturesItem>> {
        return cache.getPictures()
            .distinctUntilChanged() // ensures only events with new information get to the subscriber.
            .map { picturesList ->
                picturesList.map { it.toDomain() }
            }
    }
}