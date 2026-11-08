package com.sargedev.phoenixapp.features.profile.data.datasource

import android.util.Log
import com.sargedev.phoenixapp.BuildConfig
import com.sargedev.phoenixapp.features.profile.data.dto.UserProfileResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class UserProfileRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    private val baseUrl = BuildConfig.BASE_URL

    suspend fun getUserProfile(uid: String): UserProfileResponseDto {
        Log.d("AuthDebug", "Fetching user profile GET $baseUrl/users/profile?uid=$uid")
        return httpClient.get("$baseUrl/users/profile") {
            parameter("uid", uid)
            header("X-User-UID", uid)
        }.body()
    }
}
