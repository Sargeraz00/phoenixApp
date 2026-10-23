package com.sargedev.phoenixapp.features.auth.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sargedev.phoenixapp.features.auth.ui.viewmodel.LoginEvent
import com.sargedev.phoenixapp.features.auth.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginEvent.NavigateToHome -> onNavigateToHome()
                is LoginEvent.ShowError -> {}
            }
        }
    }

    LoginContent(
        state = state,
        onAction = { viewModel.onAction(it) },
        modifier = modifier
    )
}
