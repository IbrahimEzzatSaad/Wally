package com.example.wally.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.wally.data.cache.model.CachedFavoritePicture
import com.example.wally.data.cache.model.CachedFeaturedPicture
import com.example.wally.data.cache.model.CachedPicture
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FeaturedDao {

    @Transaction
    @Query("SELECT * FROM cached_featured_pictures")
    abstract fun getFeatured(): Flow<List<CachedFeaturedPicture>>

    @Query("DELETE FROM cached_featured_pictures")
    abstract fun deleteFeatured()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertFeatured(vararg pictures: CachedFeaturedPicture)

}