package com.sargedev.phoenixapp.features.classes.data.datasource

import android.util.Log
import com.sargedev.phoenixapp.BuildConfig
import com.sargedev.phoenixapp.features.classes.data.dto.ClassResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ClassRemoteDataSource @Inject constructor(
    private val httClient : HttpClient
) {

    private val baseUrl = BuildConfig.BASE_URL

    suspend fun getRoutineById(uid: String, gymId : String) : ClassResponseDto {
        Log.d("RoutineDebug", "Fetching members list GET $baseUrl/members/list")
        return httClient.get("$baseUrl/users/routines") {
            contentType(ContentType.Application.Json)
            header("X-User-UID", uid)
            header("X-Gym-ID", gymId)
            parameter("uid",uid)
        }.body()
    }
}