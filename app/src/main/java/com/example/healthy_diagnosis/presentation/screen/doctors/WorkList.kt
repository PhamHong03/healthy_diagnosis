

package com.example.healthy_diagnosis.presentation.screen.doctors

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthy_diagnosis.core.utils.TopBarScreen
import com.example.healthy_diagnosis.domain.usecases.patient.PatientWithApplicationDate
import com.example.healthy_diagnosis.presentation.viewmodel.ApplicationFormViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PhysicianViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.healthy_diagnosis.presentation.viewmodel.AppointmentFormViewModel
import com.example.healthy_diagnosis.ui.theme.MenuItemColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@Composable
fun WorkList(
    physicianViewModel: PhysicianViewModel,
    appointmentFormViewModel: AppointmentFormViewModel,
    navController: NavController
) {
    val physicianId by physicianViewModel.physicianId.collectAsState()
    val patients by physicianViewModel.patients.observeAsState(emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var selectedPatient by remember { mutableStateOf<PatientWithApplicationDate?>(null) }

    LaunchedEffect(physicianId) {
        physicianId?.let { id -> physicianViewModel.fetchPatients(id) }
    }

    Scaffold(
        topBar = { TopBarScreen(title = "Lịch khám đã đặt trước", onBackClick = { }) }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(patients.sortedBy { it.application_form_date }) { patient ->
                ApplicationFormItem(
                    patient = patient,
                    onDelete = { /* Xử lý xóa bệnh nhân */ },
                    onViewDetails = { /* Xử lý xem chi tiết */ },
                    onStartExam = {
                        val encodedName = Uri.encode(patient.patient_name)
                        Log.d("Upload", "patientId=${patient.application_form_id}")
                        navController.navigate("diagnosis/${patient.application_form_id}/${patient.patient_id}/$encodedName")
                    }

                )
            }
        }
    }
}

@Composable
fun ApplicationFormItem(
    patient: PatientWithApplicationDate,
    onDelete: (Int) -> Unit,
    onViewDetails: (Int) -> Unit,
    onStartExam: () -> Unit
) {
    val formattedDate = try {
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        outputFormat.format(inputFormat.parse(patient.application_form_date) ?: "N/A")
    } catch (e: Exception) {
        "N/A"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE3F2FD))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Bệnh nhân: ${patient.patient_name}", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Ngày khám: $formattedDate", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onStartExam,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Khám lịch", color = Color.White)
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { onViewDetails(patient.patient_id) }) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Chi tiết", tint = MenuItemColor)
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = { onDelete(patient.patient_id) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Xoá", tint = Color.Red)
                }
            }
        }
    }
}