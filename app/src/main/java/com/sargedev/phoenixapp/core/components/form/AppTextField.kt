package com.sargedev.phoenixapp.core.components.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sargedev.phoenixapp.core.ui.theme.InputBackground
import com.sargedev.phoenixapp.core.ui.theme.InputBorder
import com.sargedev.phoenixapp.core.ui.theme.PlaceholderColor
import com.sargedev.phoenixapp.core.ui.theme.SoftPink

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    onClick: (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (!label.isNullOrEmpty()) {
            Text(
                text = label,
                style = TextStyle(
                    color = SoftPink,
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (onClick != null) {
                        Modifier.clickable(onClick = onClick)
                    } else {
                        Modifier
                    }
                )
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = if (onClick != null) false else enabled,
                readOnly = readOnly,
                textStyle = TextStyle(
                    color = SoftPink,
                    fontSize = 16.sp
                ),
                placeholder = {
                    if (!placeholder.isNullOrEmpty()) {
                        Text(
                            text = placeholder,
                            color = PlaceholderColor,
                            fontSize = 16.sp
                        )
                    }
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                isError = isError,
                keyboardOptions = keyboardOptions,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputBackground,
                    unfocusedContainerColor = InputBackground,
                    disabledContainerColor = InputBackground,
                    errorContainerColor = InputBackground,
                    focusedBorderColor = InputBorder,
                    unfocusedBorderColor = InputBorder,
                    disabledBorderColor = InputBorder,
                    errorBorderColor = Color.Red,
                    focusedTextColor = SoftPink,
                    unfocusedTextColor = SoftPink,
                    disabledTextColor = SoftPink,
                    cursorColor = SoftPink
                )
            )
        }
    }
}
