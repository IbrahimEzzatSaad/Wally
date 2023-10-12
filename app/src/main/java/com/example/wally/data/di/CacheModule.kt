package com.example.wally.data.di

import android.content.Context
import androidx.room.Room
import com.example.wally.data.cache.Cache
import com.example.wally.data.cache.PicturesDatabase
import com.example.wally.data.cache.RoomCache
import com.example.wally.data.cache.daos.FavoriteDao
import com.example.wally.data.cache.daos.FeaturedDao
import com.example.wally.data.cache.daos.PageKeyDao
import com.example.wally.data.cache.daos.PicturesDao
import com.example.wally.data.cache.model.Converter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): PicturesDatabase {
            return Room.databaseBuilder(
                context,
                PicturesDatabase::class.java,
                "pictures.db"
            ).fallbackToDestructiveMigration().addTypeConverter(Converter())

                .build()
        }

        @Provides
        fun providePicturesDao(
            picturesDatabase: PicturesDatabase
        ): PicturesDao = picturesDatabase.picturesDao()

        @Provides
        fun provideFavoritesDao(
            picturesDatabase: PicturesDatabase
        ): FavoriteDao = picturesDatabase.favoritesDao()

        @Provides
        fun provideFeaturedDao(
            picturesDatabase: PicturesDatabase
        ): FeaturedDao = picturesDatabase.featuredDao()

        @Provides
        fun providePageKeyDao(
            picturesDatabase: PicturesDatabase
        ): PageKeyDao = picturesDatabase.pageKeyDao()

    }
}