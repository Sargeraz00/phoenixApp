package com.sargedev.phoenixapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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

sealed interface AppDestination {
    data object Splash : AppDestination
    data object Login : AppDestination
    data object Form : AppDestination
    data object Home : AppDestination
}

@Composable
fun PhoenixAppApp() {
    var currentDestination by remember { mutableStateOf<AppDestination>(AppDestination.Splash) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when (currentDestination) {
            AppDestination.Splash -> {
                SplashScreen(
                    onNavigateToLogin = { currentDestination = AppDestination.Login },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            AppDestination.Login -> {
                LoginScreen(
                    onNavigateToForm = { currentDestination = AppDestination.Form },
                    onNavigateToHome = { currentDestination = AppDestination.Home },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            AppDestination.Form -> {
                FormStubScreen(
                    onBack = { currentDestination = AppDestination.Login },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            AppDestination.Home -> {
                HomeStubScreen(
                    onLogout = { currentDestination = AppDestination.Splash },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun FormStubScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1F0F0C)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "form",
                style = TextStyle(
                    fontFamily = BebasNeueFontFamily,
                    fontSize = 48.sp,
                    color = Color(0xFFFBDCD5),
                    letterSpacing = 2.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Primera vez iniciando sesión (isFirstLogin: true)",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0xFFFFB4A5)
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFB4A5),
                    contentColor = Color(0xFF1F0F0C)
                )
            ) {
                Text("Volver")
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
            .background(Color(0xFF1F0F0C)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "home",
                style = TextStyle(
                    fontFamily = BebasNeueFontFamily,
                    fontSize = 48.sp,
                    color = Color(0xFFFBDCD5),
                    letterSpacing = 2.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Inicio de sesión existente (isFirstLogin: false)",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0xFFFFB4A5)
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFB4A5),
                    contentColor = Color(0xFF1F0F0C)
                )
            ) {
                Text("CERRAR SESIÓN")
            }
        }
    }
}
