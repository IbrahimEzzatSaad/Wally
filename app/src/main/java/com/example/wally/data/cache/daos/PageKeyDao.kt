package com.example.wally.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wally.data.cache.model.PageKeyEntity

@Dao
interface PageKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPageKey(pageKeyEntity: PageKeyEntity)

    @Query("SELECT nextPageKey FROM page_keys")
    fun getNexPage(): Long

    @Query("DELETE FROM page_keys")
    fun clearPageKeys()

}
