package com.ibrahimezzat404.wally.data.cache

import androidx.paging.PagingSource
import com.ibrahimezzat404.wally.data.cache.daos.FavoriteDao
import com.ibrahimezzat404.wally.data.cache.daos.FeaturedDao
import com.ibrahimezzat404.wally.data.cache.daos.PageKeyDao
import com.ibrahimezzat404.wally.data.cache.daos.PicturesDao
import com.ibrahimezzat404.wally.data.cache.model.CachedFavoritePicture
import com.ibrahimezzat404.wally.data.cache.model.CachedFeaturedPicture
import com.ibrahimezzat404.wally.data.cache.model.CachedPicture
import com.ibrahimezzat404.wally.data.cache.model.PageKeyEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val picturesDao: PicturesDao,
    private val favoriteDao: FavoriteDao,
    private val featuredDao: FeaturedDao,
    private val pageKeyDao: PageKeyDao
) : Cache {

    override fun getPictures(): PagingSource<Int, CachedPicture> = picturesDao.getPictures()

    override suspend fun insertPictures(vararg pictures: CachedPicture) =
        picturesDao.insertPictures(*pictures)

    override fun deletePictures() = picturesDao.deletePictures()

    override suspend fun getPictureById(id: String): CachedPicture? = picturesDao.getPictureById(id)

    override fun getItemsCount() : Int = picturesDao.getItemsCount()
    override suspend fun updatePictureToFavorite(id: String) = picturesDao.updatePictureToFavorite(id)



    override fun getFeatured(): Flow<List<CachedFeaturedPicture>> = featuredDao.getFeatured()

    override suspend fun insertFeatured(vararg pictures: CachedFeaturedPicture)  = featuredDao.insertFeatured(*pictures)

    override fun deleteFeatured() = featuredDao.deleteFeatured()



    override suspend fun getFavorite(): Flow<List<CachedFavoritePicture>> = favoriteDao.getFavorite()

    override suspend fun insertFavorite(picture: CachedFavoritePicture) = favoriteDao.insertFavorite(picture)

    override suspend fun deleteFavorite(id : String) = favoriteDao.deleteFavorite(id)

    override suspend fun getFavoriteById(id: String): CachedFavoritePicture?  = favoriteDao.getFavoriteById(id)



    override suspend fun insertPageKey(pageKeyEntity: PageKeyEntity) = pageKeyDao.insertPageKey(pageKeyEntity)

    override fun getNexPage(): Long = pageKeyDao.getNexPage()

    override fun clearPageKeys() = pageKeyDao.clearPageKeys()


}