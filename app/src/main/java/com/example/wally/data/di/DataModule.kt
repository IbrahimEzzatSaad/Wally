package com.example.wally.data.di

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import com.example.wally.data.PicturesRepositoryImp
import com.example.wally.domain.repository.PicturesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindPicturesRepository(repository: PicturesRepositoryImp): PicturesRepository


}