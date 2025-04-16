package com.example.healthy_diagnosis.presentation.screen.doctors

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.presentation.viewmodel.DiagnosisViewModel
import com.example.healthy_diagnosis.core.utils.TopBarScreen

@Composable
fun ResultScreen(
    navController: NavController,
    appointmentFormId: Int?,
    diagnosisViewModel: DiagnosisViewModel
) {
    LaunchedEffect(appointmentFormId) {
        Log.d("ResultScreen", "Received appointmentFormId: $appointmentFormId")
        appointmentFormId?.let {
            Log.d("ResultScreen", "Loading diagnosis detail for appointmentId: $it")
            diagnosisViewModel.loadDiagnosisDetail(it)
        }
    }

    Scaffold(
        topBar = {
            TopBarScreen(title = "Kết quả chẩn đoán", onBackClick = { navController.popBackStack() })
        }
    ) { paddingValues ->
        val diagnosisDetail = diagnosisViewModel.diagnosisDetail.collectAsState().value
        Log.d("ResultScreen", "Diagnosis detail loaded: $diagnosisDetail")
        diagnosisDetail?.let {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                item {
                    // Display Diagnosis Info
                    Text(text = "Mô tả: ${it.appointment_description}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Tên bệnh: ${it.disease_name}")
                    Text(text = "Mô tả bệnh: ${it.disease_description}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Danh mục bệnh: ${it.category_name}")
                    Text(text = "Mô tả danh mục bệnh: ${it.category_description}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Ngày sinh bệnh nhân: ${it.day_of_birth}")
                    Text(text = "Tên bệnh nhân: ${it.patient_name}")
                    Text(text = "Số điện thoại: ${it.phone}")
                    Spacer(modifier = Modifier.height(8.dp))

                    // Optional: Display image if available
                    it.image_path?.let { imagePath ->
                        Text(text = "Đường dẫn ảnh: $imagePath")
                    }
                }
            }
        } ?: run {
            // Handle case when diagnosis detail is not available
            Text(text = "Đang tải dữ liệu...")
        }
    }
}
