package com.ibrahimezzat404.wally.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ibrahimezzat404.wally.data.api.PicturesApi
import com.ibrahimezzat404.wally.data.cache.Cache
import com.ibrahimezzat404.wally.data.cache.model.CachedPicture
import com.ibrahimezzat404.wally.data.cache.model.PageKeyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PicturesRemoteMediator(
    private val cache: Cache,
    private val picturesApi: PicturesApi
) : RemoteMediator<Int, CachedPicture>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CachedPicture>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    withContext(Dispatchers.IO) {
                        val next = cache.getNexPage()
                        Log.i("Next Page: ", next.toString())
                        cache.insertPageKey(pageKeyEntity = PageKeyEntity(0, next + 1, next))
                        return@withContext next
                    }
                }
            }


            val response = picturesApi.getPictures(loadKey)
            if (loadType == LoadType.REFRESH) {
                withContext(Dispatchers.IO) {
                    cache.clearPageKeys()
                    cache.deletePictures()
                    cache.insertPageKey(pageKeyEntity = PageKeyEntity(0, 2 , 1))
                }
            }

            // Insert new pictures into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            withContext(Dispatchers.IO) {
                response.forEach {
                    if (cache.getPictureById(it.id) == null) {
                        val isFavorite = cache.getFavoriteById(it.id)
                        cache.insertPictures(CachedPicture.fromDomain(it, isFavorite != null))
                    }
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = response.isEmpty()
            )

        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

}