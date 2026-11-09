package com.sargedev.phoenixapp.features.dashboard.data.mapper

import com.sargedev.phoenixapp.features.dashboard.data.dto.AccessQrResponseDto
import com.sargedev.phoenixapp.features.dashboard.data.dto.DashboardResponseDto
import com.sargedev.phoenixapp.features.dashboard.domain.model.AccessQr
import com.sargedev.phoenixapp.features.dashboard.domain.model.DashboardMembership
import com.sargedev.phoenixapp.features.dashboard.domain.model.DashboardStreak
import com.sargedev.phoenixapp.features.dashboard.domain.model.DashboardUserInfo
import com.sargedev.phoenixapp.features.dashboard.domain.model.UserDashboard

fun DashboardResponseDto.toDomain(): UserDashboard {
    return UserDashboard(
        user = DashboardUserInfo(
            uid = data.user.uid,
            name = data.user.name,
            photoUrl = data.user.photoUrl
        ),
        membership = DashboardMembership(
            planName = data.membership.planName,
            price = data.membership.price,
            currency = data.membership.currency,
            nextPaymentAt = data.membership.nextPaymentAt,
            status = data.membership.status,
            daysRemaining = data.membership.daysRemaining
        ),
        streak = DashboardStreak(
            streakDays = data.streak.streakDays,
            maxStreak = data.streak.maxStreak,
            weeklyProgress = data.streak.weeklyProgress,
            weeklyCompleted = data.streak.weeklyCompleted,
            weeklyTarget = data.streak.weeklyTarget
        )
    )
}

fun AccessQrResponseDto.toDomain(): AccessQr {
    return AccessQr(
        qrContent = data.qrContent,
        parentUid = data.parentUid,
        expiresAt = data.expiresAt,
        expiresAtUnix = data.expiresAtUnix
    )
}
