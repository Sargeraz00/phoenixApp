package com.sargedev.phoenixapp.features.profile.domain.repository

import com.sargedev.phoenixapp.features.profile.domain.model.UserProfile

interface UserProfileRepository {
    suspend fun getUserProfile(uid: String): Result<UserProfile>
}
