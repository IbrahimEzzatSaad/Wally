package com.ibrahimezzat404.wally.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ibrahimezzat404.wally.data.cache.model.CachedFeaturedPicture
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FeaturedDao {

    @Query("SELECT * FROM cached_featured_pictures")
    abstract fun getFeatured(): Flow<List<CachedFeaturedPicture>>

    @Query("DELETE FROM cached_featured_pictures")
    abstract fun deleteFeatured()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertFeatured(vararg pictures: CachedFeaturedPicture)

}