package com.sargedev.phoenixapp.features.profile.data.mapper

import com.sargedev.phoenixapp.features.profile.data.dto.CompleteProfileRequestDto
import com.sargedev.phoenixapp.features.profile.domain.model.Profile

fun Profile.toDto(): CompleteProfileRequestDto {
    return CompleteProfileRequestDto(
        phoneNumber = phoneNumber,
        emergencyContact = emergencyContact,
        birthdate = birthdate,
        precondition = precondition,
        planId = planId
    )
}
