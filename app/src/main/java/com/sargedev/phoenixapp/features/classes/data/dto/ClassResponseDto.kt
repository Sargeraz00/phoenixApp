package com.sargedev.phoenixapp.features.classes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClassResponseDto(
    val status: String,
    val responseCode: String,
    val message: String,
    val correlationId: String,
    val data: List<ClassDto> = emptyList()
)