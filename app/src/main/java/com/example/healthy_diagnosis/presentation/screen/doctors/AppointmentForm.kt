package com.example.healthy_diagnosis.presentation.screen.doctors

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.healthy_diagnosis.core.utils.TopBarScreen
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormResponse
import com.example.healthy_diagnosis.domain.usecases.patient.PatientWithApplicationDate
import com.example.healthy_diagnosis.presentation.viewmodel.ApplicationFormViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.AppointmentFormViewModel
import java.text.SimpleDateFormat
import java.util.Locale

import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.healthy_diagnosis.ui.theme.BannerColor
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AppointmentForm(
    patient: PatientWithApplicationDate,
    onDismiss: () -> Unit,
    onConfirm: (AppointmentFormResponse) -> Unit,
    appointmentFormViewModel: AppointmentFormViewModel,
    navController: NavController
) {
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    val formattedDate = try {
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        outputFormat.format(inputFormat.parse(patient.application_form_date) ?: "N/A")
    } catch (e: Exception) {
        "N/A"
    }
    LaunchedEffect(Unit) {
        appointmentFormViewModel.eventFlow.collectLatest { message ->
            Log.d("AppointmentForm", "Nhận event: $message")
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            if (message == "Thêm phiếu khám thành công!") {
                Log.d("AppointmentForm", "Điều hướng đến màn hình chẩn đoán")
                navController.navigate("diagnosis/${patient.application_form_id}") {
                    popUpTo("healthcare") { inclusive = false }
                }
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Phiếu khám bệnh") },
        text = {
            Column {
                Text("Bệnh nhân: ${patient.patient_name}", fontWeight = FontWeight.Bold)
                Log.d("AppointmentForm", "Patient ID: ${patient.application_form_id}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Ngày khám: $formattedDate", fontWeight = FontWeight.Medium)
                Text("id application form : ${patient.application_form_id}", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Mô tả từ bệnh nhân: ${patient.application_form_content}", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Mô tả") })
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = BannerColor),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Log.d("AppointmentForm", "Gửi API với ID: ${patient.application_form_id}")
                val appointmentForm = AppointmentFormResponse(
                    id = 0,
                    description = description,
                    application_form_id = patient.application_form_id
                )
                onConfirm(appointmentForm)
            }) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Hủy")
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}