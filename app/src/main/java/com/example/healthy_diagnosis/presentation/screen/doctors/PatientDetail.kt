package com.example.healthy_diagnosis.presentation.screen.doctors

import android.util.Log
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
import com.example.healthy_diagnosis.core.utils.TopBarScreen
import com.example.healthy_diagnosis.presentation.viewmodel.PatientDetailsViewModel
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentDetails
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.example.healthy_diagnosis.presentation.viewmodel.ImagesViewModel

private const val BASE_URL = "http://192.168.70.24:5000/"
@Composable
fun PatientDetail(
    patientId: Int?,
    viewModel: PatientDetailsViewModel = hiltViewModel(),
    navController: NavController,
    imagesViewModel: ImagesViewModel
) {
    LaunchedEffect(patientId) {
        patientId?.let {
            viewModel.loadDiagnosisDetail(it)
        }
    }
    LaunchedEffect(Unit) {
        imagesViewModel.fetchImages()
    }

    val appointments by viewModel.diagnosisDetail.collectAsState()
    LaunchedEffect(appointments) {
        appointments.forEachIndexed { index, item ->
            Log.d("AppointmentLog", "[$index] $item")
        }
    }
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
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text("Ngày khám: ${appointment.application_form_date}")
                    Text("Bệnh: ${appointment.category_name ?: "Chưa có"}")
                    Text("Giai đoạn: ${appointment.disease_description ?: "Không xác định"}")
                    Text("Giai đoạn: ${appointment.patient_name ?: "Không xác định"}")
                    Text("Giai đoạn: ${appointment.category_description ?: "Không xác định"}")
                    Text("Giai đoạn: ${appointment.phone ?: "Không xác định"}")
                }

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Xem hình")
                }
            }

            if (expanded) {
                appointment.image_path?.let { relativePath ->
                    val fullImageUrl = BASE_URL + relativePath
                    Log.d("Image URL", "Image full URL: $fullImageUrl")

                    Spacer(modifier = Modifier.height(8.dp))

                    AsyncImage(
                        model = fullImageUrl,
                        contentDescription = "Ảnh kết quả chẩn đoán",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = 16.dp)
                    )
                } ?: run {
                    Text(
                        text = "Không có ảnh chẩn đoán",
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


