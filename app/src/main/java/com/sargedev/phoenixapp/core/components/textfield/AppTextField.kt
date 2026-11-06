package com.sargedev.phoenixapp.core.components.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sargedev.phoenixapp.core.ui.theme.InputBackground
import com.sargedev.phoenixapp.core.ui.theme.InputBorder
import com.sargedev.phoenixapp.core.ui.theme.PlaceholderColor
import com.sargedev.phoenixapp.core.ui.theme.SoftPink

class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(8)
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 1 || i == 3) {
                out += "/"
            }
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 3) return offset + 1
                if (offset <= 8) return offset + 2
                return 10
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                if (offset <= 10) return offset - 2
                return 8
            }
        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(10)
        val out = "+52 $trimmed"

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset + 4
            }

            override fun transformedToOriginal(offset: Int): Int {
                return (offset - 4).coerceAtLeast(0)
            }
        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    leadingIconRes: Int? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    trailingIconRes: Int? = null,
    iconTint: Color = SoftPink,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    onClick: (() -> Unit)? = null
) {
    Column(modifier = modifier) {
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
                enabled = if (onClick != null) true else enabled,
                readOnly = readOnly || (onClick != null),
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
                leadingIcon = when {
                    leadingIcon != null -> leadingIcon
                    leadingIconRes != null -> {
                        {
                            Icon(
                                painter = painterResource(id = leadingIconRes),
                                contentDescription = null,
                                tint = iconTint,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    else -> null
                },
                trailingIcon = when {
                    trailingIcon != null -> trailingIcon
                    trailingIconRes != null -> {
                        {
                            Icon(
                                painter = painterResource(id = trailingIconRes),
                                contentDescription = null,
                                tint = iconTint,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    else -> null
                },
                isError = isError,
                keyboardOptions = keyboardOptions,
                visualTransformation = visualTransformation,
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
