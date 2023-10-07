package com.example.wally.data.cache

import androidx.paging.PagingSource
import com.example.wally.data.cache.daos.PicturesDao
import com.example.wally.data.cache.model.CachedPicture
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val picturesDao: PicturesDao,
) : Cache {

    override fun getPictures(): PagingSource<Int, CachedPicture> = picturesDao.getPictures()
    override fun getFeatured(): Flow<List<CachedPicture>> = picturesDao.getFeatured()

    override fun getPictureById(id: String): CachedPicture? = picturesDao.getPictureById(id)

    override suspend fun storePictures(vararg pictures: CachedPicture) =
        picturesDao.insertPictures(*pictures)


    override fun deletePictures() = picturesDao.deletePictures()

    override fun deleteFeatured() = picturesDao.deleteFeatured()

    override fun getItemsCount() : Int = picturesDao.getItemsCount()

}