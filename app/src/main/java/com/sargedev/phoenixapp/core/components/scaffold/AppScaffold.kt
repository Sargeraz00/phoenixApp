package com.sargedev.phoenixapp.core.components.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sargedev.phoenixapp.core.ui.theme.BebasNeueFontFamily
import com.sargedev.phoenixapp.core.ui.theme.PhoenixBackground
import com.sargedev.phoenixapp.core.ui.theme.PhoenixCardBorder
import com.sargedev.phoenixapp.core.ui.theme.PhoenixPeach

@Composable
fun AppScaffold(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PhoenixBackground,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(PhoenixBackground)
                    .border(
                        width = 1.dp,
                        color = PhoenixCardBorder
                    )
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Left icon (near the edge)
                if (navigationIcon != null) {
                    Box(modifier = Modifier.align(Alignment.CenterStart)) {
                        navigationIcon()
                    }
                }

                // Title in center
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = BebasNeueFontFamily,
                        fontSize = 24.sp,
                        color = PhoenixPeach,
                        letterSpacing = 1.5.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )

                // Right icon (near the edge)
                if (actions != null) {
                    Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                        actions()
                    }
                }
            }
        }
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
