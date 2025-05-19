package com.ibrahimezzat404.wally.data.cache.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ibrahimezzat404.wally.data.cache.model.CachedPicture

@Dao
abstract class PicturesDao {

    @Query("SELECT * FROM cached_pictures")
    abstract fun getPictures(): PagingSource<Int, CachedPicture>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPictures(vararg pictures: CachedPicture)

    @Query("DELETE FROM cached_pictures")
    abstract fun deletePictures()

    @Query("SELECT COUNT(*) FROM cached_pictures")
    abstract fun getItemsCount() : Int

    @Query("SELECT * FROM cached_pictures WHERE id = :id")
    abstract suspend fun getPictureById(id: String) : CachedPicture?

    @Query("UPDATE cached_pictures SET favorite = NOT favorite WHERE id = :id")
    abstract suspend fun updatePictureToFavorite(id: String)


}