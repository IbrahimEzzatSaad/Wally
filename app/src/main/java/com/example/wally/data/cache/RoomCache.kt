package com.example.wally.data.cache

import com.example.wally.data.cache.daos.PicturesDao
import com.example.wally.data.cache.model.CachedPicture
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val picturesDao: PicturesDao,
) : Cache {

    override fun getPictures(): Flow<List<CachedPicture>> = picturesDao.getAllArticles()

    override fun getPicture(code: String): Flow<CachedPicture> = picturesDao.getArticleById(code)

    override suspend fun storePictures(vararg pictures: CachedPicture) =
        picturesDao.insertPictures(*pictures)

    override fun deleteAll() = picturesDao.deleteAll()

}