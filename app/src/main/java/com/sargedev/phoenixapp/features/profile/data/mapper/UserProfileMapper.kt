package com.sargedev.phoenixapp.features.profile.data.mapper

import com.sargedev.phoenixapp.features.profile.data.dto.UserProfileResponseDto
import com.sargedev.phoenixapp.features.profile.domain.model.UserProfile

fun UserProfileResponseDto.toDomain(): UserProfile {
    val d = data
    return UserProfile(
        uid = d.uid,
        fullname = d.fullname,
        email = d.email,
        role = d.role,
        status = d.status,
        phoneNumber = d.phoneNumber,
        emergencyContact = d.emergencyContact,
        birthdate = d.birthdate,
        precondition = d.precondition,
        photoUrl = d.photoUrl
    )
}
