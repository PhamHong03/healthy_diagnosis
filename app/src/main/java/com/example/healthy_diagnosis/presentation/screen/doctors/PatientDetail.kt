package com.example.healthy_diagnosis.presentation.screen.doctors

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healthy_diagnosis.core.utils.PatientItem
import com.example.healthy_diagnosis.core.utils.TopBarScreen
import com.example.healthy_diagnosis.presentation.viewmodel.PatientDetailsViewModel
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentDetails
import com.example.healthy_diagnosis.presentation.viewmodel.AppointmentFormViewModel

@Composable
fun PatientDetail(
    patientId: Int?,
    viewModel: PatientDetailsViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(patientId) {
        patientId?.let {
            viewModel.loadDiagnosisDetail(it)
        }
    }

    val appointments by viewModel.diagnosisDetail.collectAsState()
    Scaffold(
        topBar = {
            TopBarScreen(
                title = "Chi tiết lịch khám",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            if (appointments.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Không có lịch sử khám bệnh.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(appointments) { appointment ->
                        AppointmentItem(appointment)
                    }
                }
            }
        }
    }

}

@Composable
fun AppointmentItem(appointment: AppointmentDetails) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Ngày khám: ${appointment.application_form_date}")
            Text("Mô tả: ${appointment.appointment_description}")
            Text("Bệnh: ${appointment.disease_name ?: "Chưa có"}")
            Text("Loại bệnh: ${appointment.category_name ?: "Không xác định"}")
        }
    }
}
