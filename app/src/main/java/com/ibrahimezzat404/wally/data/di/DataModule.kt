package com.ibrahimezzat404.wally.data.di

import com.ibrahimezzat404.wally.data.PicturesRepositoryImp
import com.ibrahimezzat404.wally.domain.repository.PicturesRepository
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
    abstract fun bindPicturesRepository(repository: PicturesRepositoryImp): PicturesRepository


}