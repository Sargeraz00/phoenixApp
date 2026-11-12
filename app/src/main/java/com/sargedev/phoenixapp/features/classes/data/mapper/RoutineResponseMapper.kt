package com.sargedev.phoenixapp.features.classes.data.mapper

import com.sargedev.phoenixapp.features.classes.data.dto.RoutineDto
import com.sargedev.phoenixapp.features.classes.domain.model.Routine

fun RoutineDto.toDomain() : Routine {
    return Routine (
        id = id,
        createdBy = createdBy,
        name = name,
        category = category,
        description = description,
        imageUrl = imageUrl,
        status = status,
        totalExercises = totalExercises,
        exercises = exercises.map { it.toDomain() }
    )
}