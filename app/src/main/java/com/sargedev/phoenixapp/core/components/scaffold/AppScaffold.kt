package com.sargedev.phoenixapp.core.components.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sargedev.phoenixapp.core.ui.theme.BebasNeueFontFamily
import com.sargedev.phoenixapp.core.ui.theme.PhoenixBackground
import com.sargedev.phoenixapp.core.ui.theme.PhoenixCardBorder
import com.sargedev.phoenixapp.core.ui.theme.PhoenixPeach
import com.sargedev.phoenixapp.core.ui.theme.SoftPink

@Composable
fun AppScaffold(
    title: String,
    modifier: Modifier = Modifier,
    leftIconRes: Int? = null,
    onLeftClick: (() -> Unit)? = null,
    rightIconRes: Int? = null,
    onRightClick: (() -> Unit)? = null,
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
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        drawLine(
                            color = PhoenixCardBorder,
                            start = Offset(0f, size.height - strokeWidth / 2),
                            end = Offset(size.width, size.height - strokeWidth / 2),
                            strokeWidth = strokeWidth
                        )
                    }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Left icon (near the edge)
                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    if (navigationIcon != null) {
                        navigationIcon()
                    } else if (leftIconRes != null) {
                        Icon(
                            painter = painterResource(id = leftIconRes),
                            contentDescription = "Left Icon",
                            tint = SoftPink,
                            modifier = Modifier
                                .size(24.dp)
                                .then(
                                    if (onLeftClick != null) {
                                        Modifier.clickable(onClick = onLeftClick)
                                    } else {
                                        Modifier
                                    }
                                )
                        )
                    }
                }

                // Title in center
                Text(
                    title,
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
                Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                    if (actions != null) {
                        actions()
                    } else if (rightIconRes != null) {
                        Icon(
                            painter = painterResource(id = rightIconRes),
                            contentDescription = "Right Icon",
                            tint = SoftPink,
                            modifier = Modifier
                                .size(24.dp)
                                .then(
                                    if (onRightClick != null) {
                                        Modifier.clickable(onClick = onRightClick)
                                    } else {
                                        Modifier
                                    }
                                )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
