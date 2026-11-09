package com.sargedev.phoenixapp.features.profile.domain.usecase

import com.sargedev.phoenixapp.features.profile.domain.model.FamilyMember
import com.sargedev.phoenixapp.features.profile.domain.repository.MemberRepositoryNew
import javax.inject.Inject

class GetMembersUseCase @Inject constructor(
    private val repository: MemberRepositoryNew
) {
    suspend operator fun invoke(uid: String, gymId: String): Result<List<FamilyMember>> {
        return repository.getMembersList(uid, gymId)
    }
}
