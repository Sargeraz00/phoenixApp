package com.sargedev.phoenixapp.features.classes.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sargedev.phoenixapp.core.session.UserSession
import com.sargedev.phoenixapp.features.classes.domain.model.UserRoutine
import com.sargedev.phoenixapp.features.classes.domain.usecase.GetRoutineByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserRoutineUiState (
    val userRoutine : UserRoutine? = null,
    val isLoading : Boolean = false,
    val errorMessage : String? = null
)

@HiltViewModel
class UserRoutinesViewModel @Inject constructor(
    private val getRoutineByIdUseCase: GetRoutineByIdUseCase,
    private val userSession: UserSession
) : ViewModel() {

    var state by mutableStateOf(UserRoutineUiState())
        private set

    init {
        fetchRoutineByIdUser()
    }

    fun fetchRoutineByIdUser () {
        val uid = userSession.userId
        val gymId = userSession.gymId

        if (uid.isNullOrBlank() || gymId.isNullOrBlank()){
            state = state.copy(
                isLoading = false,
                errorMessage = "Credenciales faltantes"
            )
            Log.d("AuthDebug", "AccountProfileViewModel: Aborted fetch. Missing credentials -> uid: $uid, gymId: $gymId")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)

            val routineDeffer = async { getRoutineByIdUseCase(uid, gymId) }
            val routineResult = routineDeffer.await()

            var newState = state.copy(isLoading = false)

            routineResult.onSuccess { routine ->
                newState = newState.copy(userRoutine = routine)
            }.onFailure { e ->
                Log.e("AuthDebug", "UserRoutineViewModel: Error loading members: ${e.message}")
            }

            state = newState
        }
    }

}