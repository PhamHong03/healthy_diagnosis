package com.example.healthy_diagnosis.core.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PasswordVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformed = "*".repeat(text.length)
        return TransformedText(AnnotatedString(transformed), OffsetMapping.Identity)
    }
}