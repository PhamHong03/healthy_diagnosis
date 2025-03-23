

package com.example.healthy_diagnosis.presentation.screen.doctors

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthy_diagnosis.ui.theme.BannerColor
import com.example.healthy_diagnosis.ui.theme.MenuItemColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
@Composable
fun WorkList(
    modifier: Modifier = Modifier,
    physicianViewModel: PhysicianViewModel
) {
    val physicianId by physicianViewModel.physicianId.collectAsState()
    val patients by physicianViewModel.patients.observeAsState(emptyList())
    LaunchedEffect(physicianId) {
        physicianId?.let { id ->
            physicianViewModel.fetchPatients(id)
        }
    }

    Scaffold(
        topBar = {
            TopBarScreen(title = "Lịch khám đã đặt trước", onBackClick = { })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(patients.sortedBy { it.application_form_date }) { patient ->
                ApplicationFormItem(
                    patient = patient,
                    onDelete = { patientId -> /* Xử lý xóa bệnh nhân */ },
                    onViewDetails = { patientId -> /* Xử lý xem chi tiết */ }
                )
            }
        }
    }
}


@Composable
fun ApplicationFormItem(
    patient: PatientWithApplicationDate,
    onDelete: (Int) -> Unit,
    onViewDetails: (Int) -> Unit
) {
    val formattedDate = try {
        when (val dateValue = patient.application_form_date) {
            is String -> {
                val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH) // Định dạng của dữ liệu đầu vào
                val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Định dạng mong muốn
                val date = inputFormat.parse(dateValue) // Chuyển chuỗi thành Date
                outputFormat.format(date) // Format lại thành chuỗi chỉ chứa ngày tháng năm
            }
            else -> "N/A"
        }
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
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Bệnh nhân: ${patient.patient_name}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ngày khám: ${formattedDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Màu xanh lá
                ) {
                    Text("Khám lịch", color = Color.White)
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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