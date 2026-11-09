package com.sargedev.phoenixapp.features.profile.data.repository

import com.sargedev.phoenixapp.features.profile.data.datasource.MemberDataSource
import com.sargedev.phoenixapp.features.profile.data.mapper.toDomain
import com.sargedev.phoenixapp.features.profile.domain.model.FamilyMember
import com.sargedev.phoenixapp.features.profile.domain.repository.MemberRepositoryNew
import javax.inject.Inject

class MemberRepositoryNewImpl @Inject constructor(
    private val dataSource: MemberDataSource
) : MemberRepositoryNew {
    override suspend fun getMembersList(uid: String, gymId: String): Result<List<FamilyMember>> {
        return try {
            val response = dataSource.getMembersList(uid, gymId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
