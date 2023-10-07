package com.example.wally.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.wally.data.api.ApiParameters.PAGE_SIZE
import com.example.wally.data.api.PicturesApi
import com.example.wally.data.cache.Cache
import com.example.wally.data.cache.model.CachedPicture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PicturesRemoteMediator(
    private val cache: Cache,
    private val picturesApi: PicturesApi) : RemoteMediator<Int, CachedPicture>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
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
                        val count = cache.getItemsCount()
                        Log.i("Page number:","$count / $PAGE_SIZE = " +  ((count / PAGE_SIZE) + 1).toString())
                        return@withContext (count / PAGE_SIZE) + 1
                    }
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.

            val response = picturesApi.getPictures(loadKey.toLong())
            if (loadType == LoadType.REFRESH) {
                withContext(Dispatchers.IO) {
                    cache.deletePictures()
                }
            }

            // Insert new movies into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            withContext(Dispatchers.IO) {
                response.forEach {
                    cache.storePictures(CachedPicture.fromDomain(it))
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