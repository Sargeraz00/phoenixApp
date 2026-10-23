package com.sargedev.phoenixapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sargedev.phoenixapp.features.auth.ui.screen.LoginScreen
import com.sargedev.phoenixapp.features.splash.ui.screen.SplashScreen
import com.sargedev.phoenixapp.ui.theme.BebasNeueFontFamily
import com.sargedev.phoenixapp.ui.theme.PhoenixAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhoenixAppTheme {
                PhoenixAppApp()
            }
        }
    }
}

@Composable
fun PhoenixAppApp() {
    var currentScreen by rememberSaveable { mutableStateOf("splash") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Crossfade(
            targetState = currentScreen,
            modifier = Modifier.padding(innerPadding),
            label = "ScreenTransition"
        ) { screen ->
            when (screen) {
                "splash" -> {
                    SplashScreen(
                        onNavigateToLogin = { currentScreen = "login" }
                    )
                }
                "login" -> {
                    LoginScreen(
                        onNavigateToHome = { currentScreen = "home" }
                    )
                }
                "home" -> {
                    HomeStubScreen(
                        onLogout = { currentScreen = "splash" }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeStubScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "¡LOGIN EXITOSO!",
                style = TextStyle(
                    fontFamily = BebasNeueFontFamily,
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 2.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Phoenix Boxing Academy — MVP Local",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("CERRAR SESIÓN")
            }
        }
    }
}
