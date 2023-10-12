package com.example.wally.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "page_keys")
data class PageKeyEntity(
    @PrimaryKey
    val id: Int, // You can use a unique identifier for each page
    val nextPageKey: Long = 2,
    val prevPageKey: Long = 1
)