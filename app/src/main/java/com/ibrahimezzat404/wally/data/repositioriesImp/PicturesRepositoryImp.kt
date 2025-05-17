package com.ibrahimezzat404.wally.data.repositioriesImp

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ibrahimezzat404.wally.data.api.ApiParameters
import com.ibrahimezzat404.wally.data.api.PicturesApi
import com.ibrahimezzat404.wally.data.api.model.ApiPictures
import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.data.cache.Cache
import com.ibrahimezzat404.wally.data.cache.model.CachedFavoritePicture
import com.ibrahimezzat404.wally.data.cache.model.CachedFeaturedPicture
import com.ibrahimezzat404.wally.data.paging.CategoryPagingSource
import com.ibrahimezzat404.wally.data.paging.PicturesRemoteMediator
import com.ibrahimezzat404.wally.data.paging.SearchPagingSource
import com.ibrahimezzat404.wally.data.repositories.PicturesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PicturesRepositoryImp @Inject constructor(
    private val cache: Cache,
    private val api: PicturesApi
) : PicturesRepository {


    @OptIn(ExperimentalPagingApi::class)
    override suspend fun requestPictures(): Flow<PagingData<PictureModel>> {
        val pager = Pager(
            config = PagingConfig(pageSize = ApiParameters.PAGE_SIZE, enablePlaceholders = true),
            remoteMediator = PicturesRemoteMediator(
                cache, api
            )
        ) {
            cache.getPictures()
        }


        return pager.flow.map { data ->
            data.map { it.toDomain(it.favorite) }
        }

    }

    override suspend fun requestFeatured(): List<PictureModel> {

        val results: ApiPictures = api.getFeatured()
        return results.toList()

    }

    override suspend fun storeFeatured(pictures: List<PictureModel>) {
        cache.deleteFeatured()
        pictures.forEach { picture ->
            cache.insertFeatured(CachedFeaturedPicture.fromDomain(picture))
        }
    }

    override suspend fun getFeatured(): Flow<List<PictureModel>> {
        return cache.getFeatured()
            .distinctUntilChanged()
            .map { picturesList ->
                picturesList.map { it.toDomain().copy(favorite = if (cache.getFavoriteById(it.id) == null) false else true) }
            }
    }

    override suspend fun getFavorite(): Flow<List<PictureModel>> {
         return cache.getFavorite().distinctUntilChanged()
             .map {
             it.map { it.toDomain(true) }
         }

    }

    override suspend fun getCategoryList(id: String): Flow<PagingData<PictureModel>> {
        return Pager(
            config = PagingConfig(pageSize = ApiParameters.PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { CategoryPagingSource(api, id) }
        ).flow.map { pagingData ->

            pagingData.map { pictureItem ->

                pictureItem.copy(favorite = if (cache.getFavoriteById(pictureItem.id) == null) false else true)
            }
        }
    }

    override suspend fun getSearch(query: String): Flow<PagingData<PictureModel>> {
        return Pager(
            config = PagingConfig(pageSize = ApiParameters.PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { SearchPagingSource(api, query) }
        ).flow.map { pagingData ->

            pagingData.map { pictureItem ->

                pictureItem.copy(favorite = if (cache.getFavoriteById(pictureItem.id) == null) false else true)
            }
        }
    }

    override suspend fun updateFavorite(item: PictureModel) {
        if (cache.getFavoriteById(item.id) == null) {
            cache.insertFavorite(CachedFavoritePicture.fromDomain(item))
            cache.updatePictureToFavorite(item.id)
        } else
            cache.deleteFavorite(item.id)
    }
}