package com.sargedev.phoenixapp.features.classes.domain.repository

import com.sargedev.phoenixapp.features.classes.domain.model.Routine
import com.sargedev.phoenixapp.features.classes.domain.model.UserRoutine

interface ClassRepository {

    suspend fun getRoutineById (uid: String, gymId : String): Result<UserRoutine>
    suspend fun createRoutine (routine : Routine) : Result<Unit>
    suspend fun assignRoutine (uid : String, idRoutine : String, status : String) : Result<Unit>
}