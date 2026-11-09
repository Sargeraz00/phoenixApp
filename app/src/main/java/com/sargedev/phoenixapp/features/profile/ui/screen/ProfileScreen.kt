package com.sargedev.phoenixapp.features.profile.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.sargedev.phoenixapp.R
import com.sargedev.phoenixapp.core.components.scaffold.AppScaffold
import com.sargedev.phoenixapp.core.ui.theme.*
import com.sargedev.phoenixapp.features.profile.ui.viewmodel.AccountProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

fun Modifier.dashedBorder(strokeWidth: Dp, color: Color, cornerRadius: Dp): Modifier = this.drawWithContent {
    drawContent()
    val stroke = Stroke(
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), 0f)
    )
    drawRoundRect(
        color = color,
        style = stroke,
        cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx()),
        size = size,
        topLeft = Offset.Zero
    )
}

fun formatNextPayment(dateStr: String?): String {
    if (dateStr.isNullOrEmpty()) return "30 Nov"
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val formatter = SimpleDateFormat("d MMM", Locale.ENGLISH)
        val date = parser.parse(dateStr)
        if (date != null) formatter.format(date) else dateStr
    } catch (_: Exception) {
        dateStr
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountProfileViewModel = viewModel()
) {
    val state = viewModel.state
    val userProfile = state.userProfile
    val subscription = state.subscription
    val members = state.members
    val scrollState = rememberScrollState()
    val membersScrollState = rememberScrollState()

    val photoUrl = userProfile?.photoUrl ?: FirebaseAuth.getInstance().currentUser?.photoUrl?.toString()
    val fullName = userProfile?.fullname ?: FirebaseAuth.getInstance().currentUser?.displayName ?: "DIEGO RAMÍREZ"
    val status = userProfile?.status ?: "ACTIVE"
    val price = subscription?.price ?: 120.0
    val nextPayment = formatNextPayment(subscription?.nextPaymentAt)

    val creationTimestamp = FirebaseAuth.getInstance().currentUser?.metadata?.creationTimestamp ?: System.currentTimeMillis()
    val memberSinceFormatter = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
    val memberSinceText = "Member since ${memberSinceFormatter.format(Date(creationTimestamp))}"

    val memberCountText = "${members.size}/4"

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
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Card 1: User Info Card (Column, centered)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = InputBackground, // #1E1E1E
                    border = BorderStroke(1.dp, InputBorder) // #2C2C2C
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Profile Avatar on top
                        Box(
                            modifier = Modifier
                                .size(76.dp)
                                .clip(CircleShape)
                                .border(2.dp, PhoenixCardBorder, CircleShape)
                                .background(PhoenixCardBorder),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!photoUrl.isNullOrEmpty()) {
                                AsyncImage(
                                    model = photoUrl,
                                    contentDescription = "Profile Photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.phoenix),
                                    contentDescription = "Profile Photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Full name
                        Text(
                            text = fullName.uppercase(),
                            style = TextStyle(
                                fontFamily = BebasNeueFontFamily,
                                fontSize = 24.sp,
                                color = SoftPink, // #FBDCD5
                                letterSpacing = 1.2.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Member since (dynamic from first login metadata)
                        Text(
                            text = memberSinceText,
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = PhoenixTextSub // #E5BDB6
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Status in a box with rounded corners
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .background(StatusActiveBackground)
                                .border(1.dp, StatusActiveBorder, RoundedCornerShape(50.dp))
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(TimerGreen)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = status.uppercase(),
                                    style = TextStyle(
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TimerGreen,
                                        letterSpacing = 0.8.sp
                                    )
                                )
                            }
                        }
                    }
                }

                // Card 2: Membership & Billing
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = InputBackground, // #1E1E1E
                    border = BorderStroke(1.dp, InputBorder)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Title header with icon
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_credit_card_new),
                                contentDescription = "Membership",
                                tint = SoftPink,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "MEMBERSHIP & BILLING",
                                style = TextStyle(
                                    fontFamily = BebasNeueFontFamily,
                                    fontSize = 18.sp,
                                    color = SoftPink, // #FBDCD5
                                    letterSpacing = 1.2.sp
                                )
                            )
                        }

                        // Divider bottom border #43302C
                        HorizontalDivider(
                            color = PhoenixCardBorder,
                            thickness = 1.dp
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Small card inside: bg #281714
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = CardDarkBackground, // #281714
                                border = BorderStroke(1.dp, PhoenixCardBorder)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "NEXT PAYMENT",
                                            style = TextStyle(
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = PhoenixTextSub, // #E5BDB6
                                                letterSpacing = 0.8.sp
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = nextPayment,
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SoftPink // #FBDCD5
                                            )
                                        )
                                    }

                                    Column(
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            text = "AMOUNT",
                                            style = TextStyle(
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = PhoenixTextSub,
                                                letterSpacing = 0.8.sp
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "$${String.format(Locale.ENGLISH, "%.2f", price)}",
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = SoftPink
                                            )
                                        )
                                    }
                                }
                            }

                            // 2 buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                val buttonGradient = Brush.horizontalGradient(
                                    colors = listOf(ButtonGradientStart, ButtonGradientEnd)
                                )
                                Button(
                                    onClick = { },
                                    modifier = Modifier
                                        .weight(1f)
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
                                        Text(
                                            text = "UPDATE PAYMENT METHOD",
                                            style = TextStyle(
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                letterSpacing = 0.5.sp
                                            )
                                        )
                                    }
                                }

                                OutlinedButton(
                                    onClick = { },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = InputBackground
                                    ),
                                    border = BorderStroke(1.dp, InputBorder)
                                ) {
                                    Text(
                                        text = "VIEW HISTORY",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SoftPink,
                                            letterSpacing = 0.5.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // Card 3: Family Members (Background equal to other cards #1E1E1E, border #2C2C2C)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = InputBackground, // #1E1E1E
                    border = BorderStroke(1.dp, InputBorder) // #2C2C2C
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Title header with badge on top right (1/4 format)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_members_new),
                                    contentDescription = "Family",
                                    tint = SoftPink,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "FAMILY MEMBERS",
                                    style = TextStyle(
                                        fontFamily = BebasNeueFontFamily,
                                        fontSize = 18.sp,
                                        color = SoftPink, // #FBDCD5
                                        letterSpacing = 1.2.sp
                                    )
                                )
                            }

                            // Member count badge top right (e.g. 0/4 or 1/4)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(PhoenixBackground)
                                    .border(1.dp, PhoenixCardBorder, RoundedCornerShape(50.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = memberCountText,
                                    style = TextStyle(
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PhoenixTextSub
                                    )
                                )
                            }
                        }

                        HorizontalDivider(
                            color = PhoenixCardBorder,
                            thickness = 1.dp
                        )

                        // Member cards with scrollable overflow container if needed
                        if (members.isEmpty()) {
                            Text(
                                text = "No registered family members yet.",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = PhoenixTextSub.copy(alpha = 0.7f)
                                )
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 240.dp)
                                    .verticalScroll(membersScrollState)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    members.forEach { member ->
                                        Surface(
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(12.dp),
                                            color = CardDarkBackground, // #281714
                                            border = BorderStroke(1.dp, PhoenixCardBorder)
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(12.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(44.dp)
                                                        .clip(CircleShape)
                                                        .background(PhoenixBackground),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    if (!member.photoUrl.isNullOrEmpty()) {
                                                        AsyncImage(
                                                            model = member.photoUrl,
                                                            contentDescription = "Member Photo",
                                                            modifier = Modifier.fillMaxSize(),
                                                            contentScale = ContentScale.Crop
                                                        )
                                                    } else {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.phoenix),
                                                            contentDescription = "Member Photo",
                                                            modifier = Modifier.size(24.dp),
                                                            contentScale = ContentScale.Crop
                                                        )
                                                    }
                                                }

                                                Spacer(modifier = Modifier.width(12.dp))

                                                Column(
                                                    modifier = Modifier.weight(1f)
                                                ) {
                                                    Text(
                                                        text = member.name,
                                                        style = TextStyle(
                                                            fontSize = 14.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = SoftPink
                                                        )
                                                    )
                                                    Spacer(modifier = Modifier.height(2.dp))
                                                    Text(
                                                        text = "(${member.relationship.uppercase()})",
                                                        style = TextStyle(
                                                            fontSize = 11.sp,
                                                            color = PhoenixTextSub
                                                        )
                                                    )
                                                }

                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_options_new),
                                                    contentDescription = "Options",
                                                    tint = PhoenixTextSub,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Add Member button: Column with icon on top, then + ADD MEMBER pure text, dashed border rounded box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .dashedBorder(1.dp, PhoenixCardBorder, 12.dp)
                                .clickable { }
                                .padding(vertical = 18.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_add_user_new),
                                    contentDescription = "Add Member",
                                    tint = SoftPink,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "+ ADD MEMBER",
                                    style = TextStyle(
                                        fontFamily = BebasNeueFontFamily,
                                        fontSize = 15.sp,
                                        color = SoftPink, // #FBDCD5
                                        letterSpacing = 1.2.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
