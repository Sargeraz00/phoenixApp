package com.sargedev.phoenixapp.features.profile.domain.repository

import com.sargedev.phoenixapp.features.profile.domain.model.FamilyMember

interface MemberRepositoryNew {
    suspend fun getMembersList(uid: String, gymId: String): Result<List<FamilyMember>>
}
