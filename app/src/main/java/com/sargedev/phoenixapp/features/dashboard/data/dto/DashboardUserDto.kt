package com.sargedev.phoenixapp.features.dashboard.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DashboardResponseDto(
    val status: String,
    val responseCode: String,
    val message: String,
    val correlationId: String,
    val data: DashboardDataDto
)

@Serializable
data class DashboardDataDto(
    val user: DashboardUserInfoDto,
    val membership: DashboardMembershipDto,
    val streak: DashboardStreakDto
)

@Serializable
data class DashboardUserInfoDto(
    val uid: String,
    val name: String,
    val photoUrl: String? = null
)

@Serializable
data class DashboardMembershipDto(
    val planName: String,
    val price: Double,
    val currency: String,
    val nextPaymentAt: String? = null,
    val status: String,
    val daysRemaining: Int
)

@Serializable
data class DashboardStreakDto(
    val streakDays: Int,
    val maxStreak: Int,
    val weeklyProgress: String,
    val weeklyCompleted: Int,
    val weeklyTarget: Int
)
