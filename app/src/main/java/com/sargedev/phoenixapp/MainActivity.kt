package com.sargedev.phoenixapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sargedev.phoenixapp.core.components.navigation.AppBottomNavBar
import com.sargedev.phoenixapp.core.components.navigation.NavigationTab
import com.sargedev.phoenixapp.features.auth.ui.screen.LoginScreen
import com.sargedev.phoenixapp.features.classes.ui.screen.ClassesScreen
import com.sargedev.phoenixapp.features.dashboard.ui.screen.DashboardScreen
import com.sargedev.phoenixapp.features.profile.ui.screen.CompleteProfile
import com.sargedev.phoenixapp.features.profile.ui.screen.ProfileScreen
import com.sargedev.phoenixapp.features.splash.ui.screen.SplashScreen
import com.sargedev.phoenixapp.core.ui.theme.PhoenixAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
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
                CompleteProfile(
                    onActivateSuccess = { currentDestination = AppDestination.Home },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            AppDestination.Home -> {
                MainHomeScreen(
                    onLogout = { currentDestination = AppDestination.Splash },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun MainHomeScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(NavigationTab.DASHBOARD) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            AppBottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        when (selectedTab) {
            NavigationTab.DASHBOARD -> DashboardScreen(modifier = Modifier.padding(innerPadding))
            NavigationTab.CLASSES -> ClassesScreen(modifier = Modifier.padding(innerPadding))
            NavigationTab.PROFILE -> ProfileScreen(modifier = Modifier.padding(innerPadding))
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
