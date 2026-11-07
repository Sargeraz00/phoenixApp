package com.sargedev.phoenixapp.features.dashboard.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.sargedev.phoenixapp.R
import com.sargedev.phoenixapp.core.components.scaffold.AppScaffold
import com.sargedev.phoenixapp.core.ui.theme.*
import com.sargedev.phoenixapp.features.dashboard.ui.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = viewModel()
) {
    val state = viewModel.state
    val profile = state.userProfile
    val scrollState = rememberScrollState()

    val photoUrl = profile?.photoUrl ?: FirebaseAuth.getInstance().currentUser?.photoUrl?.toString()
    val fullName = profile?.fullname ?: FirebaseAuth.getInstance().currentUser?.displayName ?: "Usuario"
    val firstName = fullName.split(" ").firstOrNull() ?: fullName

    AppScaffold(
        title = "PHOENIX ACADEMY",
        modifier = modifier,
        leftIconRes = R.drawable.ic_dumbbell
    ) { innerModifier ->
        Column(
            modifier = innerModifier
                .fillMaxSize()
                .background(PhoenixBackground)
                .verticalScroll(scrollState)
                .padding(start = 24.dp, top = 72.dp, end = 24.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top User Info (Left-aligned, slightly down from top)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Avatar from Profile API / Firebase Auth
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(PhoenixCardBorder),
                    contentAlignment = Alignment.Center
                ) {
                    if (!photoUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = "User Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.phoenix),
                            contentDescription = "User Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "ACCESS STATUS",
                        style = TextStyle(
                            color = SoftPink,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "HOLA, ${firstName.uppercase()}",
                        style = TextStyle(
                            fontFamily = BebasNeueFontFamily,
                            color = SoftPink,
                            fontSize = 26.sp,
                            letterSpacing = 1.5.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // QR Code Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                    .size(220.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_qr_code),
                        contentDescription = "Access QR Code",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Refresh in legend with ic_clock
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = "Clock",
                    tint = TimerGreen,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Refresh in",
                    style = TextStyle(
                        color = PhoenixTextSub,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "2H 59M",
                    style = TextStyle(
                        color = TimerGreen,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            // 2 Cards Below
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Card 1: Membership (Status indicator in top right corner)
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(130.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardDarkBackground),
                    border = BorderStroke(1.dp, PhoenixCardBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Top Right Status Indicator
                        Row(
                            modifier = Modifier.align(Alignment.TopEnd),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(TimerGreen)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Activa",
                                style = TextStyle(
                                    color = TimerGreen,
                                    fontSize = 11.sp
                                )
                            )
                        }

                        // Content
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_fast),
                                    contentDescription = "Membership",
                                    tint = SoftPink,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Membresía",
                                    style = TextStyle(
                                        color = PhoenixTextSub,
                                        fontSize = 13.sp
                                    )
                                )
                            }
                            Text(
                                text = "Plan Pro",
                                style = TextStyle(
                                    color = SoftPink,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                // Card 2: Streak
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(130.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardDarkBackground),
                    border = BorderStroke(1.dp, PhoenixCardBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_streak),
                                contentDescription = "Streak",
                                tint = SoftPink,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Racha",
                                style = TextStyle(
                                    color = PhoenixTextSub,
                                    fontSize = 13.sp
                                )
                            )
                        }

                        Column {
                            Text(
                                text = "5 días",
                                style = TextStyle(
                                    color = PhoenixTextSub,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "¡Vas con todo!",
                                style = TextStyle(
                                    color = PhoenixTextSub,
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
