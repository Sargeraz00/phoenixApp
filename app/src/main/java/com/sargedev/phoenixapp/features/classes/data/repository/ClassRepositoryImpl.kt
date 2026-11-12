package com.sargedev.phoenixapp.features.classes.data.repository

import com.sargedev.phoenixapp.features.classes.data.datasource.ClassRemoteDataSource
import com.sargedev.phoenixapp.features.classes.data.mapper.toDomain
import com.sargedev.phoenixapp.features.classes.domain.model.Routine
import com.sargedev.phoenixapp.features.classes.domain.model.UserRoutine
import com.sargedev.phoenixapp.features.classes.domain.repository.ClassRepository
import javax.inject.Inject

class ClassRepositoryImpl @Inject constructor(
    private val remoteDataSource : ClassRemoteDataSource
) : ClassRepository {

    override suspend fun getRoutineById(uid: String, gymId: String): Result<UserRoutine> {
        return try {
            val response = remoteDataSource.getRoutineById(uid,gymId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createRoutine(routine: Routine): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun assignRoutine(
        uid: String,
        idRoutine: String,
        status: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }
}