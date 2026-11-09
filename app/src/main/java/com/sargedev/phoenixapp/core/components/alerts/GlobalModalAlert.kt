package com.sargedev.phoenixapp.core.components.alerts

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sargedev.phoenixapp.R
import com.sargedev.phoenixapp.core.components.button.AppButton
import com.sargedev.phoenixapp.core.components.button.IconPosition
import com.sargedev.phoenixapp.core.ui.theme.ModalDefaultBackground
import com.sargedev.phoenixapp.core.ui.theme.ModalDefaultBorder
import com.sargedev.phoenixapp.core.ui.theme.ModalErrorBackground
import com.sargedev.phoenixapp.core.ui.theme.ModalErrorTitleText
import com.sargedev.phoenixapp.core.ui.theme.ModalProgressBackground
import com.sargedev.phoenixapp.core.ui.theme.ModalProgressBorder
import com.sargedev.phoenixapp.core.ui.theme.ModalProgressOrange
import com.sargedev.phoenixapp.core.ui.theme.ModalSuccessGreen
import com.sargedev.phoenixapp.core.ui.theme.ModalTextDescription

enum class AlertStatus {
    IN_PROGRESS,
    SUCCESS,
    ERROR
}

fun Modifier.outwardGlow(
    color: Color,
    circleSize: Dp = 80.dp,
    glowSpread: Dp = 28.dp
): Modifier = this.drawBehind {
    val center = this.center
    val radius = (circleSize.toPx() / 2) + glowSpread.toPx()

    val glowBrush = Brush.radialGradient(
        colors = listOf(
            color.copy(alpha = 0.55f),
            color.copy(alpha = 0.35f),
            color.copy(alpha = 0.15f),
            Color.Transparent
        ),
        center = center,
        radius = radius
    )

    drawCircle(
        brush = glowBrush,
        radius = radius,
        center = center
    )
}

@Composable
fun GlobalModalAlert(
    title: String,
    description: String,
    iconRes: Int,
    status: AlertStatus,
    onDismissRequest: () -> Unit = {},
    onPrimaryAction: () -> Unit = {},
    onSecondaryAction: () -> Unit = {},
) {
    val modalBackgroundColor = if (status == AlertStatus.IN_PROGRESS) ModalProgressBackground else ModalDefaultBackground
    val modalBorderColor = if (status == AlertStatus.IN_PROGRESS) ModalProgressBorder else ModalDefaultBorder

    val statusGlowColor = when (status) {
        AlertStatus.IN_PROGRESS -> ModalProgressOrange
        AlertStatus.SUCCESS -> ModalSuccessGreen
        AlertStatus.ERROR -> ModalErrorBackground
    }

    val animatedIndicatorColor by animateColorAsState(
        targetValue = statusGlowColor,
        label = "IndicatorColor"
    )

    val titleColor = when (status) {
        AlertStatus.IN_PROGRESS -> ModalProgressOrange
        AlertStatus.SUCCESS -> ModalSuccessGreen
        AlertStatus.ERROR -> ModalErrorTitleText
    }

    Dialog(
        onDismissRequest = {
            if (status != AlertStatus.IN_PROGRESS) {
                onDismissRequest()
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = status != AlertStatus.IN_PROGRESS,
            dismissOnClickOutside = status != AlertStatus.IN_PROGRESS,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                shape = RoundedCornerShape(28.dp),
                color = modalBackgroundColor,
                border = BorderStroke(1.dp, modalBorderColor),
                tonalElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(96.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .outwardGlow(
                                    color = statusGlowColor,
                                    circleSize = 80.dp,
                                    glowSpread = 28.dp
                                )
                        )

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(80.dp)
                        ) {
                            when (status) {
                                AlertStatus.IN_PROGRESS -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier.fillMaxSize(),
                                        color = animatedIndicatorColor,
                                        trackColor = ModalProgressBorder.copy(alpha = 0.4f),
                                        strokeWidth = 6.dp
                                    )
                                }

                                AlertStatus.SUCCESS -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .background(Color.Transparent)
                                            .border(width = 3.dp, color = ModalSuccessGreen, shape = CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {}
                                }

                                AlertStatus.ERROR -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .background(ModalErrorBackground)
                                            .border(width = 3.dp, color = Color(0xFF5D3736), shape = CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {}
                                }
                            }
                        }

                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            tint = if (status == AlertStatus.IN_PROGRESS) ModalProgressOrange else Color.Unspecified,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = titleColor,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = ModalTextDescription,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    when (status) {
                        AlertStatus.IN_PROGRESS -> { }

                        AlertStatus.SUCCESS -> {
                            AppButton(
                                text = "Cerrar",
                                onClick = onPrimaryAction,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        AlertStatus.ERROR -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                AppButton(
                                    text = "Reintentar",
                                    onClick = onPrimaryAction,
                                    modifier = Modifier.fillMaxWidth(),
                                    iconRes = R.drawable.ic_reload,
                                    iconPosition = IconPosition.LEFT
                                )

                                OutlinedButton(
                                    onClick = onSecondaryAction,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, ModalProgressBorder)
                                ) {
                                    Text(
                                        text = "Cancelar",
                                        color = ModalTextDescription,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}