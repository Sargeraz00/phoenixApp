package com.sargedev.phoenixapp.features.splash.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sargedev.phoenixapp.R
import com.sargedev.phoenixapp.ui.theme.*

@Composable
fun SplashContent(
    modifier: Modifier = Modifier
) {
    val diagonalFlameGradient = Brush.linearGradient(
        colors = listOf(
            PhoenixGradientStop1,
            PhoenixGradientStop2,
            PhoenixGradientStop3,
            PhoenixGradientStop4,
            PhoenixGradientStop5,
            PhoenixGradientStop4,
            PhoenixGradientStop6,
            MaterialTheme.colorScheme.background
        ),
        start = Offset(0f, 0f),
        end = Offset(1080f, 1920f)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(diagonalFlameGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Phoenix Bird Logo - even larger (300.dp)
            Image(
                painter = painterResource(id = R.drawable.phoenix),
                contentDescription = "Phoenix Logo",
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 24.dp)
            )

            // PHOENIX text (Theme primary color, Bebas Neue)
            Text(
                text = "PHOENIX",
                style = TextStyle(
                    fontFamily = BebasNeueFontFamily,
                    fontSize = 62.sp,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 4.sp,
                    shadow = Shadow(
                        color = PhoenixGlow,
                        offset = Offset(2f, 4f),
                        blurRadius = 12f
                    )
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            // BOXING · ACADEMY text (Theme onSurface/sub color)
            Text(
                text = "BOXING · ACADEMY",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 6.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shadow = Shadow(
                        color = PhoenixGlow,
                        offset = Offset(1f, 2f),
                        blurRadius = 6f
                    )
                )
            )
        }
    }
}
