package com.ibrahimezzat404.wally.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ibrahimezzat404.wally.data.cache.daos.FavoriteDao
import com.ibrahimezzat404.wally.data.cache.daos.FeaturedDao
import com.ibrahimezzat404.wally.data.cache.daos.PageKeyDao
import com.ibrahimezzat404.wally.data.cache.daos.PicturesDao
import com.ibrahimezzat404.wally.data.cache.model.CachedFavoritePicture
import com.ibrahimezzat404.wally.data.cache.model.CachedFeaturedPicture
import com.ibrahimezzat404.wally.data.cache.model.CachedPicture
import com.ibrahimezzat404.wally.data.cache.model.Converter
import com.ibrahimezzat404.wally.data.cache.model.PageKeyEntity

/**
 * Database class with a singleton INSTANCE object.
 */
@Database(entities = [CachedPicture::class, CachedFavoritePicture::class, CachedFeaturedPicture::class, PageKeyEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class PicturesDatabase : RoomDatabase() {

    abstract fun picturesDao(): PicturesDao

    abstract fun favoritesDao(): FavoriteDao

    abstract fun featuredDao(): FeaturedDao

    abstract fun pageKeyDao(): PageKeyDao


}