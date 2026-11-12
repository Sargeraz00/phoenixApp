package com.sargedev.phoenixapp.features.classes.data.mapper

import com.sargedev.phoenixapp.features.classes.data.dto.ExerciseDto
import com.sargedev.phoenixapp.features.classes.domain.model.Exercise

fun ExerciseDto.toDomain() : Exercise {
    return Exercise (
        id = id,
        name = name,
        sets = sets,
        repetitions = reps,
        durationInSec = durationSec,
        restSeconds = restSec,
        notes = notes
    )
}