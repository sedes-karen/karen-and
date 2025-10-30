package com.example.karen_and.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AppInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = Color.Gray,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    hasBorder: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null
) {

    val indicatorColor = if (hasBorder) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Transparent
    }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                if (placeholder != null) {
                    Text(text = placeholder)
                }
            },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = containerColor,
                focusedContainerColor = containerColor,
                unfocusedTextColor = contentColor,
                focusedTextColor = contentColor,
                unfocusedIndicatorColor = indicatorColor,
                focusedIndicatorColor = indicatorColor,
                disabledContainerColor = Color.Gray,
                disabledTextColor = Color.White,
            ),
            trailingIcon = trailingIcon,
        )
    }