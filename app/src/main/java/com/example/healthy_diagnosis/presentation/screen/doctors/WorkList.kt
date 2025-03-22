

package com.example.healthy_diagnosis.presentation.screen.doctors

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthy_diagnosis.presentation.viewmodel.ApplicationFormViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel

@Composable
fun WorkList(
    modifier: Modifier = Modifier,
    applicationFormViewModel: ApplicationFormViewModel,
    patientViewModel: PatientViewModel
) {
    // Lấy danh sách application form từ ViewModel
    val applicationForms by applicationFormViewModel.applicationFormList.collectAsState()

    // Hiển thị danh sách bằng LazyColumn
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        items(applicationForms) { applicationForm ->
            ApplicationFormItem(applicationForm.patient_id, applicationForm.application_form_date, patientViewModel)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ApplicationFormItem(
    patientId: Int,
    applicationDate: String,
    patientViewModel: PatientViewModel,
) {
    var patientName by remember { mutableStateOf("Đang tải...") }

    LaunchedEffect(patientId) {
//        patientViewModel.getPatientNameById(patientId) { name ->
//            patientName = name ?: "Không tìm thấy"
//        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Bệnh nhân: $patientName", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Ngày khám: $applicationDate", fontSize = 14.sp)
        }
    }
}
