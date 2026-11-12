package com.sargedev.phoenixapp.features.classes.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sargedev.phoenixapp.R
import com.sargedev.phoenixapp.core.components.scaffold.AppScaffold
import com.sargedev.phoenixapp.core.ui.theme.*
import com.sargedev.phoenixapp.features.classes.ui.components.RoutineCard
import com.sargedev.phoenixapp.features.classes.ui.viewmodel.UserRoutinesViewModel

@Composable
fun ClassesScreen(
    modifier: Modifier = Modifier,
    viewModel: UserRoutinesViewModel = viewModel()
) {
    val state = viewModel.state
    val userRoutine = state.userRoutine
    val scrollState = rememberScrollState()

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
                                color = SoftPink,
                                letterSpacing = 1.2.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (userRoutine != null) "Rutina asignada activa" else "Sin rutina asignada",
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = SoftPink
                            )
                        )
                    }

                    // Filters button
                    Surface(
                        onClick = { },
                        shape = RoundedCornerShape(20.dp),
                        color = InputBackground,
                        border = BorderStroke(1.dp, InputBorder)
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
                                    color = FilterGreen
                                )
                            )
                        }
                    }
                }

                // Manejo de Estados (Carga, Error o Datos)
                when {
                    state.isLoading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = SoftPink)
                        }
                    }
                    state.errorMessage != null -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.errorMessage,
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                        }
                    }
                    userRoutine != null -> {
                        RoutineCard(
                            userRoutine = userRoutine,
                            onStartRoutineClick = {
                                // TODO: Lógica al iniciar la rutina
                            },
                            onViewDetailsClick = {
                                // TODO: Lógica al ver detalles
                            }
                        )
                    }
                    else -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay rutinas disponibles.",
                                color = PhoenixTextSub,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}