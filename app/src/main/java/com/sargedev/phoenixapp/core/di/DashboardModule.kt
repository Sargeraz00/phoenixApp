package com.sargedev.phoenixapp.core.di

import com.sargedev.phoenixapp.features.dashboard.data.datasource.UserDashboardRemoteDataSource
import com.sargedev.phoenixapp.features.dashboard.data.repository.UserDashboardRepositoryImpl
import com.sargedev.phoenixapp.features.dashboard.domain.repository.UserDashboardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {

    @Provides
    @Singleton
    fun provideUserDashboardRepository(
        remoteDataSource: UserDashboardRemoteDataSource
    ): UserDashboardRepository {
        return UserDashboardRepositoryImpl(remoteDataSource)
    }
}
