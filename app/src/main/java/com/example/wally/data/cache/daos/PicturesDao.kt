package com.example.wally.data.cache.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.wally.data.cache.model.CachedPicture
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PicturesDao {

    @Transaction
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


}