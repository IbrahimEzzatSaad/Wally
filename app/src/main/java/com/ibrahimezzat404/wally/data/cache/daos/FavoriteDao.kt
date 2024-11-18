package com.ibrahimezzat404.wally.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ibrahimezzat404.wally.data.cache.model.CachedFavoritePicture
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FavoriteDao {

    @Transaction
    @Query("SELECT * FROM cached_favorite_pictures")
    abstract fun getFavorite(): Flow<List<CachedFavoritePicture>>

    @Query("DELETE FROM cached_favorite_pictures WHERE id = :id")
    abstract suspend fun deleteFavorite(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertFavorite(picture: CachedFavoritePicture)

    @Query("SELECT * FROM cached_favorite_pictures WHERE id = :id")
    abstract suspend fun getFavoriteById(id: String) : CachedFavoritePicture?


}