package com.sargedev.phoenixapp.features.profile.data.mapper

import com.sargedev.phoenixapp.features.profile.data.dto.MemberResponseDto
import com.sargedev.phoenixapp.features.profile.domain.model.FamilyMember

fun MemberResponseDto.toDomain(): List<FamilyMember> {
    return data.map { dto ->
        FamilyMember(
            id = dto.id ?: "",
            name = dto.name,
            relationship = dto.relationship ?: "Familiar",
            photoUrl = dto.photoUrl
        )
    }
}
