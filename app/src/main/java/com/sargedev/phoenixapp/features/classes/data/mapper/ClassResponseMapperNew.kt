package com.sargedev.phoenixapp.features.classes.data.mapper

import com.sargedev.phoenixapp.features.classes.data.dto.ClassResponseDto
import com.sargedev.phoenixapp.features.classes.domain.model.UserRoutine

fun ClassResponseDto.toDomain(): UserRoutine {
    val firstItem = data.firstOrNull() ?: throw NoSuchElementException("No se encontro ninguna rutina de la respuesta")

    return UserRoutine (
        id = firstItem.id,
        userUid = firstItem.userUid,
        status = firstItem.userUid,
        assignedAt = firstItem.assignedAt,
        routine = firstItem.routine.toDomain()
    )
}