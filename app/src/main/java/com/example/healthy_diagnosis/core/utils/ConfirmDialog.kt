package com.example.healthy_diagnosis.core.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmDialog(onConfirm: () -> Unit, onDismiss: () -> Unit , text: String) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Xác nhận") },
        text = { Text(text = text) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Xác nhận")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Hủy")
            }
        }
    )
}