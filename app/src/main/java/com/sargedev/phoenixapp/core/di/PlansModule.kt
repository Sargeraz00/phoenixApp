package com.sargedev.phoenixapp.core.di

import com.sargedev.phoenixapp.features.plans.data.repository.PlansRepositoryImpl
import com.sargedev.phoenixapp.features.plans.domain.repository.PlansRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlansModule {

    @Provides
    @Singleton
    fun providePlansRepository(
        plansRepositoryImpl: PlansRepositoryImpl
    ): PlansRepository {
        return plansRepositoryImpl
    }
}
