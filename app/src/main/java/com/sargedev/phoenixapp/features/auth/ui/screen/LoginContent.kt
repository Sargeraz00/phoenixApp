package com.sargedev.phoenixapp.features.auth.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sargedev.phoenixapp.R
import com.sargedev.phoenixapp.features.auth.ui.viewmodel.LoginAction
import com.sargedev.phoenixapp.features.auth.ui.viewmodel.LoginUiState
import com.sargedev.phoenixapp.ui.theme.*

@Composable
fun LoginContent(
    state: LoginUiState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // Diagonal flame gradient matching Figma design precisely
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
        // Layer 1: Large Phoenix Logo (300.dp) positioned at the top, affected by background dimming
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.phoenix),
                contentDescription = "Phoenix Logo",
                modifier = Modifier.size(300.dp)
            )
        }

        // Layer 2: Dark backdrop overlay covering background and logo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x7F000000))
        )

        // Layer 3: Centered Modal Card on top of backdrop overlay (positioned below the 300.dp logo without overlap)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(80.dp)) // Offset to place card below the large 300.dp logo

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, PhoenixCardBorder),
                shadowElevation = 12.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp, vertical = 48.dp), // Increased vertical padding to make the box taller/longer
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // PHOENIX title in Bebas Neue, theme primary
                    Text(
                        text = "PHOENIX",
                        style = TextStyle(
                            fontFamily = BebasNeueFontFamily,
                            fontSize = 44.sp,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 2.sp,
                            shadow = Shadow(
                                color = PhoenixGlow,
                                offset = Offset(2f, 4f),
                                blurRadius = 8f
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // BOXING · ACADEMY in Inter, theme onSurface
                    Text(
                        text = "BOXING · ACADEMY",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 3.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shadow = Shadow(
                                color = PhoenixGlow,
                                offset = Offset(1f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        modifier = Modifier.padding(bottom = 40.dp) // Increased bottom spacing inside card
                    )

                    // Google Login Button with original Google icon
                    Button(
                        onClick = { onAction(LoginAction.SubmitGoogleLogin) },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = DarkBrown
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = DarkBrown
                            )
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_google),
                                    contentDescription = "Google Logo",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Continuar con Google",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = DarkBrown
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp)) // Increased spacing before terms

                    // Terms and Privacy legal text (theme onSurface)
                    Text(
                        text = "Al continuar aceptas los Términos y la Privacidad de la academia.",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )
                    )
                }
            }
        }
    }
}
