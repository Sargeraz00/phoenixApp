package com.sargedev.phoenixapp.features.auth.ui.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.sargedev.phoenixapp.features.auth.ui.viewmodel.LoginAction
import com.sargedev.phoenixapp.features.auth.ui.viewmodel.LoginEvent
import com.sargedev.phoenixapp.features.auth.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onNavigateToForm: () -> Unit,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginEvent.NavigateToForm -> onNavigateToForm()
                is LoginEvent.NavigateToHome -> onNavigateToHome()
                is LoginEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LoginContent(
        state = state,
        onAction = { action ->
            when (action) {
                is LoginAction.SubmitGoogleLogin -> {
                    coroutineScope.launch {
                        try {
                            val googleIdOption = GetGoogleIdOption.Builder()
                                .setFilterByAuthorizedAccounts(false)
                                .setServerClientId("17698673890-o4ru3a0nmcq7ebrloi6483an5272q0fj.apps.googleusercontent.com")
                                .setAutoSelectEnabled(false)
                                .build()

                            val credRequest = GetCredentialRequest.Builder()
                                .addCredentialOption(googleIdOption)
                                .build()

                            val result = CredentialManager.create(context).getCredential(context, credRequest)
                            val credential = result.credential

                            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                                val idToken = googleIdTokenCredential.idToken
                                viewModel.onAction(LoginAction.SubmitGoogleLogin(idToken))
                            } else {
                                Toast.makeText(context, "Credencial de Google no válida", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error OAuth: ${e.localizedMessage ?: e.toString()}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        },
        modifier = modifier
    )
}
