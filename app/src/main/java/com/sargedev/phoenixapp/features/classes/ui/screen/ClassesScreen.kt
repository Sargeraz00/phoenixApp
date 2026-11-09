package com.sargedev.phoenixapp.features.classes.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.sargedev.phoenixapp.R
import com.sargedev.phoenixapp.core.components.scaffold.AppScaffold
import com.sargedev.phoenixapp.core.ui.theme.*

data class Exercise(
    val name: String,
    val detail: String
)

data class Routine(
    val title: String,
    val description: String,
    val status: String, // "ACTIVE" or "PROGRAMADO"
    val imageRes: Int,
    val exercises: List<Exercise>
)

@Composable
fun ClassesScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    val routines = listOf(
        Routine(
            title = "BOXING FUNDAMENTALS",
            description = "Trabajo técnico enfocado en la base del striking. Combos básicos y movimiento lateral.",
            status = "ACTIVE",
            imageRes = R.drawable.classes_card,
            exercises = listOf(
                Exercise("Jab-Cross-Hook", "3 × 3 min"),
                Exercise("Footwork drills (Escalera)", "4 Series"),
                Exercise("Heavy Bag Work", "5 × 3 min")
            )
        ),
        Routine(
            title = "MMA STRENGTH",
            description = "Circuito de fuerza explosiva para mejorar la potencia en derribos y strikes.",
            status = "PROGRAMADO",
            imageRes = R.drawable.classes_card,
            exercises = listOf(
                Exercise("Kettlebell Swings", "4 × 15 reps"),
                Exercise("Med Ball Slams", "4 × 12 reps"),
                Exercise("Farmer's Walk", "3 × 30m")
            )
        )
    )

    AppScaffold(
        title = "PHOENIX ACADEMY",
        modifier = modifier,
        leftIconRes = R.drawable.ic_dumbbell
    ) { innerModifier ->
        Box(
            modifier = innerModifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PhoenixBackground)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Header section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MIS RUTINAS",
                            style = TextStyle(
                                fontFamily = BebasNeueFontFamily,
                                fontSize = 22.sp,
                                color = SoftPink, // #FBDCD5
                                letterSpacing = 1.2.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Progreso semanal: 3/5 entrenamientos",
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = SoftPink // #FBDCD5
                            )
                        )
                    }

                    // Filters button
                    Surface(
                        onClick = { },
                        shape = RoundedCornerShape(20.dp),
                        color = InputBackground, // #1E1E1E
                        border = BorderStroke(1.dp, InputBorder) // #2C2C2C
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_filter),
                                contentDescription = "Filtros",
                                tint = FilterGreen,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Filtros",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = FilterGreen // #D7FFC5
                                )
                            )
                        }
                    }
                }

                // Routine Cards List
                routines.forEach { routine ->
                    RoutineCard(routine = routine)
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun RoutineCard(routine: Routine) {
    val isActive = routine.status.equals("ACTIVE", ignoreCase = true)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = RoutineCardBackground, // #1E1E1EB2
        border = BorderStroke(1.dp, InputBorder) // #2C2C2C
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Banner image with gradients and status badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Image(
                    painter = painterResource(id = routine.imageRes),
                    contentDescription = routine.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Horizontal gradient overlay (more transparent/faded on the left)
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

                // Vertical gradient overlay at the bottom of the image
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

                // Status Badge in top right corner (solid opaque background for both ACTIVE and PROGRAMADO)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(if (isActive) StatusActiveLightBg else ProgramadoBg)
                        .border(
                            1.dp,
                            if (isActive) StatusActiveLightBg else ProgramadoBorder,
                            RoundedCornerShape(50.dp)
                        )
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

            // Card content body
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Title and Icon row
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_boxing_glove),
                        contentDescription = "Boxing",
                        tint = SoftPink,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = routine.title,
                        style = TextStyle(
                            fontFamily = BebasNeueFontFamily,
                            fontSize = 20.sp,
                            color = SoftPink, // #FBDCD5
                            letterSpacing = 1.2.sp
                        )
                    )
                }

                // Description
                Text(
                    text = routine.description,
                    style = TextStyle(
                        fontSize = 13.sp,
                        color = PhoenixTextSub, // #E5BDB6
                        lineHeight = 18.sp
                    )
                )

                // Exercises list with bottom border only (#2C2C2C)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    routine.exercises.forEach { exercise ->
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
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = SoftPink // #FBDCD5
                                    )
                                )
                                Text(
                                    text = exercise.detail,
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = PhoenixPeach // #FFB4A5
                                    )
                                )
                            }
                            HorizontalDivider(
                                color = InputBorder, // #2C2C2C
                                thickness = 1.dp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Action Button
                if (isActive) {
                    val buttonGradient = Brush.horizontalGradient(
                        colors = listOf(ButtonGradientStart, ButtonGradientEnd)
                    )
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(buttonGradient),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_play),
                                    contentDescription = "Play",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "INICIAR RUTINA",
                                    style = TextStyle(
                                    fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        letterSpacing = 1.sp
                                    )
                                )
                            }
                        }
                    }
                } else {
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = InputBackground),
                        border = BorderStroke(1.dp, InputBorder)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar_month),
                                contentDescription = "Details",
                                tint = PhoenixTextSub,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "VER DETALLES",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PhoenixTextSub, // #E5BDB6
                                    letterSpacing = 1.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
