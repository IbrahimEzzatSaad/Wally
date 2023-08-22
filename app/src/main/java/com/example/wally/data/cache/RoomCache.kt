package com.example.wally.data.cache

import com.example.wally.data.cache.daos.PicturesDao
import com.example.wally.data.cache.model.CachedPicture
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val picturesDao: PicturesDao,
) : Cache {

    override fun getPictures(): Flow<List<CachedPicture>> = picturesDao.getPictures()
    override fun getFeatured(): Flow<List<CachedPicture>> = picturesDao.getFeatured()

    override fun getPicture(code: String): Flow<CachedPicture> = picturesDao.getPictureById(code)

    override suspend fun storePictures(vararg pictures: CachedPicture) =
        picturesDao.insertPictures(*pictures)

    override fun deletePictures() = picturesDao.deletePictures()

    override fun deleteFeatured() = picturesDao.deleteFeatured()


}