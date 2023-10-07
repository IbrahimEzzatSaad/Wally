package com.example.wally.data.cache.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.wally.data.cache.model.CachedPicture
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PicturesDao {

    @Transaction
    @Query("SELECT * FROM pictures WHERE featured = 0")
    abstract fun getPictures(): PagingSource<Int, CachedPicture>

    @Transaction
    @Query("SELECT * FROM pictures WHERE featured = 1 ORDER BY likes DESC")
    abstract fun getFeatured(): Flow<List<CachedPicture>>

    @Transaction
    @Query("SELECT * FROM pictures WHERE id = :id AND featured = 0")
    abstract fun getPictureById(id: String): CachedPicture?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPictures(vararg pictures: CachedPicture)

    @Query("DELETE FROM pictures WHERE featured = 0")
    abstract fun deletePictures()

    @Query("DELETE FROM pictures WHERE featured = 1")
    abstract fun deleteFeatured()

    @Query("SELECT COUNT(*) FROM pictures WHERE featured = 0")
    abstract fun getItemsCount() : Int


}