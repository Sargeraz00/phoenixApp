package com.sargedev.phoenixapp.core.di

import com.sargedev.phoenixapp.features.profile.data.datasource.CompleteProfileRemoteDataSource
import com.sargedev.phoenixapp.features.profile.data.repository.CompleteProfileRepositoryImpl
import com.sargedev.phoenixapp.features.profile.domain.CompleteProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideCompleteProfileRepository(
        remoteDataSource: CompleteProfileRemoteDataSource
    ): CompleteProfileRepository {
        return CompleteProfileRepositoryImpl(remoteDataSource)
    }
}
