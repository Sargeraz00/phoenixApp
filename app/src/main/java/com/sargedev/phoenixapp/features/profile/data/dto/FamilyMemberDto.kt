package com.sargedev.phoenixapp.features.profile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MemberResponseDto(
    val status: String,
    val responseCode: String,
    val message: String,
    val correlationId: String,
    val data: List<MemberItemDto> = emptyList()
)

@Serializable
data class MemberItemDto(
    val id: String? = null,
    val name: String,
    val relationship: String? = null,
    val photoUrl: String? = null
)
