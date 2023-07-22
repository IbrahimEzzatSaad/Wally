package com.example.wally.data.di

import com.example.wally.data.PicturesRepositoryImp
import com.example.wally.domain.repository.PicturesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindArticlesRepository(repository: PicturesRepositoryImp): PicturesRepository

}