package com.example.wally.data.cache.daos

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
    @Query("SELECT * FROM pictures ORDER BY likes DESC")
    abstract fun getAllPictures(): Flow<List<CachedPicture>>

    @Transaction
    @Query("SELECT * FROM pictures where code = :code")
    abstract fun getPictureById(code: String): Flow<CachedPicture>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPictures(vararg pictures: CachedPicture)

    @Query("DELETE FROM pictures")
    abstract fun deleteAll()
}