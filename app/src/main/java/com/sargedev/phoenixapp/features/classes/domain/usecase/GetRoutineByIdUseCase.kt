package com.sargedev.phoenixapp.features.classes.domain.usecase

import com.sargedev.phoenixapp.features.classes.domain.model.UserRoutine
import com.sargedev.phoenixapp.features.classes.domain.repository.ClassRepository
import javax.inject.Inject

class GetRoutineByIdUseCase @Inject constructor(
    private val repository : ClassRepository
) {

    suspend operator fun invoke (uid: String, gymId : String) : Result<UserRoutine> {
        return repository.getRoutineById(uid, gymId)
    }
}