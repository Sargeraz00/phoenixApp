package com.sargedev.phoenixapp.core.di

import com.sargedev.phoenixapp.features.profile.data.datasource.CompleteProfileRemoteDataSource
import com.sargedev.phoenixapp.features.profile.data.datasource.MemberDataSource
import com.sargedev.phoenixapp.features.profile.data.datasource.SubscriptionDataSource
import com.sargedev.phoenixapp.features.profile.data.datasource.UserProfileRemoteDataSource
import com.sargedev.phoenixapp.features.profile.data.repository.CompleteProfileRepositoryImpl
import com.sargedev.phoenixapp.features.profile.data.repository.MemberRepositoryNewImpl
import com.sargedev.phoenixapp.features.profile.data.repository.SubscriptionRepositoryNewImpl
import com.sargedev.phoenixapp.features.profile.data.repository.UserProfileRepositoryImpl
import com.sargedev.phoenixapp.features.profile.domain.repository.CompleteProfileRepository
import com.sargedev.phoenixapp.features.profile.domain.repository.MemberRepositoryNew
import com.sargedev.phoenixapp.features.profile.domain.repository.SubscriptionRepositoryNew
import com.sargedev.phoenixapp.features.profile.domain.repository.UserProfileRepository
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

    @Provides
    @Singleton
    fun provideUserProfileRepository(
        remoteDataSource: UserProfileRemoteDataSource
    ): UserProfileRepository {
        return UserProfileRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideSubscriptionRepositoryNew(
        dataSource: SubscriptionDataSource
    ): SubscriptionRepositoryNew {
        return SubscriptionRepositoryNewImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideMemberRepositoryNew(
        dataSource: MemberDataSource
    ): MemberRepositoryNew {
        return MemberRepositoryNewImpl(dataSource)
    }
}
