package com.sargedev.phoenixapp.features.profile.data.datasource

import android.util.Log
import com.sargedev.phoenixapp.BuildConfig
import com.sargedev.phoenixapp.features.profile.data.dto.CompleteProfileRequestDto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class CompleteProfileRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    private val baseUrl = BuildConfig.BASE_URL

    suspend fun completeProfile(dto: CompleteProfileRequestDto, userUid: String?) {
        Log.d("AuthDebug", "Sending POST to $baseUrl/users/complete-profile with header X-User-UID: $userUid")
        httpClient.post("$baseUrl/users/complete-profile") {
            contentType(ContentType.Application.Json)
            if (!userUid.isNullOrEmpty()) {
                header("X-User-UID", userUid)
            }
            setBody(dto)
        }
    }
}
