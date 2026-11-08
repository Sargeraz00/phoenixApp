package com.sargedev.phoenixapp.features.dashboard.domain.model

data class UserDashboard(
    val user: DashboardUserInfo,
    val membership: DashboardMembership,
    val streak: DashboardStreak
)

data class DashboardUserInfo(
    val uid: String,
    val name: String,
    val photoUrl: String?
)

data class DashboardMembership(
    val planName: String,
    val price: Double,
    val currency: String,
    val nextPaymentAt: String?,
    val status: String,
    val daysRemaining: Int
)

data class DashboardStreak(
    val streakDays: Int,
    val maxStreak: Int,
    val weeklyProgress: String,
    val weeklyCompleted: Int,
    val weeklyTarget: Int
)
