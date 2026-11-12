package com.sargedev.phoenixapp.features.classes.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sargedev.phoenixapp.R
import com.sargedev.phoenixapp.core.components.button.AppButton
import com.sargedev.phoenixapp.core.components.button.IconPosition
import com.sargedev.phoenixapp.core.ui.theme.*
import com.sargedev.phoenixapp.features.classes.domain.model.UserRoutine

@Composable
fun RoutineCard(
    userRoutine: UserRoutine,
    modifier: Modifier = Modifier,
    onStartRoutineClick: () -> Unit = {},
    onViewDetailsClick: () -> Unit = {}
) {
    val routine = userRoutine.routine
    val isActive = routine.status.equals("ACTIVE", ignoreCase = true)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = RoutineCardBackground,
        border = BorderStroke(1.dp, InputBorder)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Banner con imagen y gradientes
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                AsyncImage(
                    model = routine.imageUrl,
                    contentDescription = routine.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.classes_card),
                    error = painterResource(id = R.drawable.classes_card)
                )

                // Gradiente horizontal
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(RoutineCardBackground, RoutineCardBackground.copy(alpha = 0.4f), Color.Transparent),
                                startX = 0f,
                                endX = 500f
                            )
                        )
                )

                // Gradiente vertical
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, RoutineCardBackground),
                                startY = 50f
                            )
                        )
                )

                // Status Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(if (isActive) StatusActiveLightBg else ProgramadoBg)
                        .border(1.dp, if (isActive) StatusActiveLightBg else ProgramadoBorder, RoundedCornerShape(50.dp))
                        .padding(horizontal = 12.dp, vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = routine.status,
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isActive) StatusActiveLightText else PhoenixTextSub,
                            letterSpacing = 0.8.sp
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_boxing_glove),
                        contentDescription = "Boxing",
                        tint = SoftPink,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = routine.name,
                        style = TextStyle(
                            fontFamily = BebasNeueFontFamily,
                            fontSize = 20.sp,
                            color = SoftPink,
                            letterSpacing = 1.2.sp
                        )
                    )
                }

                Text(
                    text = routine.description,
                    style = TextStyle(
                        fontSize = 13.sp,
                        color = PhoenixTextSub,
                        lineHeight = 18.sp
                    )
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    routine.exercises.forEach { exercise ->
                        val detailText = when {
                            exercise.durationInSec > 0 -> "${exercise.sets} × ${exercise.durationInSec.toInt()}s"
                            exercise.repetitions > 0 -> "${exercise.sets} × ${exercise.repetitions} reps"
                            else -> "${exercise.sets} Series"
                        }

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = exercise.name,
                                    style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium, color = SoftPink)
                                )
                                Text(
                                    text = detailText,
                                    style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium, color = PhoenixPeach)
                                )
                            }
                            HorizontalDivider(color = InputBorder, thickness = 1.dp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                if (isActive) {
                    AppButton(
                        text = "INICIAR RUTINA",
                        onClick = onStartRoutineClick,
                        textColor = Color.White,
                        iconRes = R.drawable.ic_play,
                        iconPosition = IconPosition.LEFT,
                        gradientColors = listOf(ButtonGradientStart, ButtonGradientEnd)
                    )
                } else {
                    AppButton(
                        text = "VER DETALLES",
                        onClick = onViewDetailsClick,
                        textColor = PhoenixTextSub,
                        iconRes = R.drawable.ic_calendar_month,
                        iconPosition = IconPosition.LEFT,
                        // Simulamos un botón outline usando un color plano en el gradiente
                        gradientColors = listOf(InputBackground, InputBackground)
                    )
                }
            }
        }
    }
}