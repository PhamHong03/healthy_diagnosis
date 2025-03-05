package com.example.healthy_diagnosis.core.utils


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

import com.example.healthy_diagnosis.ui.theme.focusedTextFieldText
import com.example.healthy_diagnosis.ui.theme.textFieldContainer
import com.example.healthy_diagnosis.ui.theme.unfocusedTextFieldText

@Composable
fun TextFieldLoginRegister(
    modifier: Modifier = Modifier,
    label: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    textInput: String,
    onTextChanged: (String) -> Unit,
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false
) {
    val uiColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
    val visualTransformation = if (isPasswordField && !isPasswordVisible) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
    TextField(
        modifier = modifier,
        value = textInput,
        onValueChange = {
            onTextChanged(it)
        },
        label = {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = uiColor)
        },
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.colors(
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
            focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
            unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
            focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
        ),
        trailingIcon = trailingIcon,
        singleLine = true
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewTextField() {
    TextFieldLoginRegister(label = "", textInput = "", onTextChanged = {})
}