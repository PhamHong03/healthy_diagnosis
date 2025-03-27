package com.example.healthy_diagnosis.presentation.screen.customers

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.core.utils.BannerInfo
import com.example.healthy_diagnosis.core.utils.ButtonClick
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCustomer(
    navController: NavController,
    patientViewModel: PatientViewModel,
    authViewModel: AuthViewModel
) {


    val currentUser by authViewModel.account.collectAsState()
    val accountId = currentUser?.id ?:0
    val patientId by patientViewModel.patientId.collectAsState()

    LaunchedEffect(accountId) {
        if (accountId != 0) {
            patientViewModel.fetchPatientIdByAccountId(accountId)
        }
    }

    Scaffold(
        topBar = {
            BannerInfo(padding = 0.dp, "Trang chủ bệnh nhân")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Chào mừng bạn đến với Trung tâm khám chữa bệnh",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            ButtonClick(
                text = "Đăng ký khám bệnh",
                onClick = { navController.navigate("booking/{patientId}") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonClick(
                text = "Xem lịch sử khám",
                onClick = { navController.navigate("medical_history") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonClick(
                text = "Danh sách bác sĩ",
                onClick = { navController.navigate("doctor_list") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonClick(
                text = "Thông tin cá nhân",
                onClick = { navController.navigate("profile") }
            )
        }
    }
}
