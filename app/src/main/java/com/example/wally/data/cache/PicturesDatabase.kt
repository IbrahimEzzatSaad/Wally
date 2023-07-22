package com.example.wally.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wally.data.cache.daos.PicturesDao
import com.example.wally.data.cache.model.CachedPicture
import com.example.wally.data.cache.model.Converter

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [CachedPicture::class], version = 1)
@TypeConverters(Converter::class)
abstract class PicturesDatabase : RoomDatabase() {

    abstract fun picturesDao(): PicturesDao

}