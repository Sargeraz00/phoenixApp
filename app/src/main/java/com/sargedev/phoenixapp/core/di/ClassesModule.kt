package com.sargedev.phoenixapp.core.di

import com.sargedev.phoenixapp.features.classes.data.repository.ClassRepositoryImpl
import com.sargedev.phoenixapp.features.classes.domain.repository.ClassRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClassesModule {

    @Provides
    @Singleton
    fun provideClassRepository(
        classRepositoryImpl: ClassRepositoryImpl
    ): ClassRepository {
        return classRepositoryImpl
    }
}
