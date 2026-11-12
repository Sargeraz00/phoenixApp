package com.sargedev.phoenixapp.features.profile.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sargedev.phoenixapp.core.session.UserSession
import com.sargedev.phoenixapp.features.profile.domain.model.FamilyMember
import com.sargedev.phoenixapp.features.profile.domain.model.UserSubscription
import com.sargedev.phoenixapp.features.profile.domain.model.UserProfile
import com.sargedev.phoenixapp.features.profile.domain.usecase.GetSubscriptionUseCase
import com.sargedev.phoenixapp.features.profile.domain.usecase.GetMembersUseCase
import com.sargedev.phoenixapp.features.profile.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountProfileUiState(
    val userProfile: UserProfile? = null,
    val subscription: UserSubscription? = null,
    val members: List<FamilyMember> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class AccountProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getSubscriptionUseCase: GetSubscriptionUseCase,
    private val getMembersUseCase: GetMembersUseCase,
    private val userSession: UserSession
) : ViewModel() {

    var state by mutableStateOf(AccountProfileUiState())
        private set

    init {
        fetchProfileData()
    }

    fun fetchProfileData() {
        val uid = userSession.userId
        val gymId = userSession.gymId

        if (uid.isNullOrBlank() || gymId.isNullOrBlank()) {
            state = state.copy(
                isLoading = false,
                errorMessage = "Sesión no válida o incompleta. Por favor, inicia sesión de nuevo."
            )
            Log.e("AuthDebug", "AccountProfileViewModel: Aborted fetch. Missing credentials -> uid: $uid, gymId: $gymId")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)

            val profileDeferred = async { getUserProfileUseCase(uid) }
            val subscriptionDeferred = async { getSubscriptionUseCase(uid, gymId) }
            val membersDeferred = async { getMembersUseCase(uid, gymId) }

            val profileResult = profileDeferred.await()
            val subscriptionResult = subscriptionDeferred.await()
            val membersResult = membersDeferred.await()

            var newState = state.copy(isLoading = false)

            profileResult.onSuccess { profile ->
                newState = newState.copy(userProfile = profile)
                Log.d("AuthDebug", "AccountProfileViewModel: UserProfile loaded: ${profile.fullname}")
            }.onFailure { e ->
                Log.e("AuthDebug", "AccountProfileViewModel: Error loading profile: ${e.message}")
            }

            subscriptionResult.onSuccess { sub ->
                newState = newState.copy(subscription = sub)
                Log.d("AuthDebug", "AccountProfileViewModel: Subscription loaded: ${sub.planName}")
            }.onFailure { e ->
                Log.e("AuthDebug", "AccountProfileViewModel: Error loading subscription: ${e.message}")
            }

            membersResult.onSuccess { list ->
                newState = newState.copy(members = list)
                Log.d("AuthDebug", "AccountProfileViewModel: Members list loaded: ${list.size} members")
            }.onFailure { e ->
                Log.e("AuthDebug", "AccountProfileViewModel: Error loading members: ${e.message}")
            }

            state = newState
        }
    }
}
