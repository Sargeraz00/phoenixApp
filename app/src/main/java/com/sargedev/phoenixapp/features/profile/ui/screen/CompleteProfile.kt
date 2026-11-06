package com.sargedev.phoenixapp.features.profile.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sargedev.phoenixapp.R
import com.sargedev.phoenixapp.core.components.button.AppButton
import com.sargedev.phoenixapp.core.components.button.IconPosition
import com.sargedev.phoenixapp.core.components.scaffold.AppScaffold
import com.sargedev.phoenixapp.core.components.textfield.AppTextField
import com.sargedev.phoenixapp.core.ui.theme.*
import com.sargedev.phoenixapp.features.profile.ui.viewmodel.CompleteProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteProfile(
    onActivateSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CompleteProfileViewModel = viewModel()
) {
    val state = viewModel.state
    val scrollState = rememberScrollState()

    var planExpanded by remember { mutableStateOf(false) }
    val gymPlans = listOf(
        "Plan Básico - $499 / mes",
        "Plan Pro - $899 / mes",
        "Plan VIP Anual - $7,999 / año",
        "Plan Estudiante - $399 / mes"
    )

    AppScaffold(
        title = "PHOENIX",
        modifier = modifier,
        leftIconRes = R.drawable.ic_menu_hamburger
    ) { innerModifier ->
        Column(
            modifier = innerModifier
                .fillMaxSize()
                .background(PhoenixBackground)
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Descriptive text
            Text(
                text = "Precargamos tus datos desde tu cuenta de Google.\nCompleta el resto para activar tu membresía.",
                style = TextStyle(
                    color = PhoenixTextSub,
                    fontSize = 14.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Form Box (#2C1B18CC background, #43302C border)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xCC2C1B18))
                    .border(
                        width = 1.dp,
                        color = PhoenixCardBorder,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Plan inicial (Select with ExposedDropdownMenuBox)
                    ExposedDropdownMenuBox(
                        expanded = planExpanded,
                        onExpandedChange = { planExpanded = !planExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AppTextField(
                            value = state.plan,
                            onValueChange = {},
                            label = "Plan inicial",
                            placeholder = "Mensual · $800MXN",
                            readOnly = true,
                            leadingIconRes = R.drawable.icon_streak,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = planExpanded)
                            },
                            onClick = { planExpanded = !planExpanded },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                        )

                        ExposedDropdownMenu(
                            expanded = planExpanded,
                            onDismissRequest = { planExpanded = false },
                            modifier = Modifier.background(InputBackground)
                        ) {
                            gymPlans.forEach { plan ->
                                DropdownMenuItem(
                                    text = { Text(text = plan, color = SoftPink) },
                                    onClick = {
                                        viewModel.onPlanChanged(plan)
                                        planExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Contacto de emergencia (Phone number)
                    AppTextField(
                        value = state.emergencyContact,
                        onValueChange = { viewModel.onEmergencyContactChanged(it) },
                        label = "Contacto de emergencia",
                        placeholder = "+52 000 000 0000",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        leadingIconRes = R.drawable.ic_call
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Número telefónico
                    AppTextField(
                        value = state.phoneNumber,
                        onValueChange = { viewModel.onPhoneNumberChanged(it) },
                        label = "Número telefónico",
                        placeholder = "+52 000 000 0000",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        leadingIconRes = R.drawable.ic_call
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Fecha mm/dd/yyyy
                    AppTextField(
                        value = state.birthDate,
                        onValueChange = { viewModel.onBirthDateChanged(it) },
                        label = "Fecha de nacimiento",
                        placeholder = "mm/dd/yyyy",
                        leadingIconRes = R.drawable.ic_calendar_month
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Condiciones preexistentes (long text box)
                    AppTextField(
                        value = state.preexistingConditions,
                        onValueChange = { viewModel.onPreexistingConditionsChanged(it) },
                        label = "Condiciones preexistentes",
                        placeholder = "Escribe aquí si tienes alguna condición...",
                        singleLine = false,
                        minLines = 3,
                        leadingIconRes = R.drawable.ic_healt
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Button INSIDE the box: ACTIVAR MEMBRESIA (using default linear gradient)
                    AppButton(
                        text = "ACTIVAR MEMBRESIA",
                        onClick = {
                            viewModel.submitProfile()
                            onActivateSuccess()
                        },
                        textColor = PhoenixPeach,
                        iconRes = R.drawable.ic_rocket,
                        iconPosition = IconPosition.RIGHT
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // OUTSIDE the box: ¿Eres Maestro? · Contacta a recepción (#FFB4A5 color)
            Text(
                text = "¿Eres Maestro? · Contacta a recepción",
                style = TextStyle(
                    color = PhoenixPeach,
                    fontSize = 14.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Bottom feature highlights / badges (ic_secure, ic_Fast, ic_badge_verified) with SoftPink (#FBDCD5) text and icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FeatureBadge(
                    iconRes = R.drawable.ic_secure,
                    text = "Seguro",
                    tint = Color.Unspecified
                )
                FeatureBadge(
                    iconRes = R.drawable.ic_fast,
                    text = "Rápido",
                    tint = Color.Unspecified
                )
                FeatureBadge(
                    iconRes = R.drawable.ic_badge_verified,
                    text = "Premium",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
fun FeatureBadge(
    iconRes: Int,
    text: String,
    modifier: Modifier = Modifier,
    tint : Color
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            tint = tint,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = TextStyle(
                color = SoftPink,
                fontSize = 13.sp
            )
        )
    }
}
