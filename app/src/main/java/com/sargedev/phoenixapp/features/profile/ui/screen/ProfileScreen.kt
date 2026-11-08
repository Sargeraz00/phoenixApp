package com.sargedev.phoenixapp.features.profile.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.sargedev.phoenixapp.core.components.scaffold.AppScaffold
import com.sargedev.phoenixapp.core.ui.theme.BebasNeueFontFamily
import com.sargedev.phoenixapp.core.ui.theme.PhoenixBackground
import com.sargedev.phoenixapp.core.ui.theme.SoftPink

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    AppScaffold(title = "PERFIL", modifier = modifier) { inner ->
        Box(
            modifier = inner.fillMaxSize().background(PhoenixBackground),
            contentAlignment = Alignment.Center
        ) {
            Text("Perfil", fontFamily = BebasNeueFontFamily, fontSize = 32.sp, color = SoftPink)
        }
    }
}
