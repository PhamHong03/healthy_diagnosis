package com.example.healthy_diagnosis.presentation.screen.customers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.core.utils.BannerInfo
import com.example.healthy_diagnosis.core.utils.ButtonClick
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.ui.theme.LightPink
import androidx.compose.foundation.background
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreenCustomer(
//    navController: NavController,
//    patientViewModel: PatientViewModel,
//    authViewModel: AuthViewModel
//) {
//
//
//    val currentUser by authViewModel.account.collectAsState()
//    val accountId = currentUser?.id ?:0
//    val patientId by patientViewModel.patientId.collectAsState()
//
//    LaunchedEffect(accountId) {
//        if (accountId != 0) {
//            patientViewModel.fetchPatientIdByAccountId(accountId)
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            BannerInfo(padding = 0.dp, "Trang chủ bệnh nhân")
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(20.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Chào mừng bạn đến với Trung tâm khám chữa bệnh",
//                fontSize = 18.sp,
//                color = Color.Black,
//                modifier = Modifier.padding(bottom = 20.dp)
//            )
//
//            ButtonClick(
//                text = "Đăng ký khám bệnh",
//                onClick = { navController.navigate("booking/${patientId}") }
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            ButtonClick(
//                text = "Xem lịch sử khám",
//                onClick = { navController.navigate("medical_history") }
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            ButtonClick(
//                text = "Danh sách bác sĩ",
//                onClick = { navController.navigate("doctor_list") }
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            ButtonClick(
//                text = "Thông tin cá nhân",
//                onClick = { navController.navigate("profile") }
//            )
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCustomer(
    navController: NavController,
    patientViewModel: PatientViewModel,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.account.collectAsState()
    val accountId = currentUser?.id ?: 0
    val patientId by patientViewModel.patientId.collectAsState()

    LaunchedEffect(accountId) {
        if (accountId != 0) {
            patientViewModel.fetchPatientIdByAccountId(accountId)
        }
    }

    Scaffold(
        topBar = {
            BannerInfo(padding = 0.dp, text = "Trang chủ bệnh nhân")
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color(0xFFBBDEFB), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🩺 Chăm sóc sức khỏe toàn diện\nDành cho bạn & gia đình",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            // ✅ Grid 2 cột
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ButtonCustomer(
                        text = "Đăng ký khám bệnh",
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("booking/$patientId") }
                    )
                    ButtonCustomer(
                        text = "Lịch khám đã đặt",
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("medical_history") }
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ButtonCustomer(
                        text = "Hồ sơ bệnh án",
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("doctor_list") }
                    )
                    ButtonCustomer(
                        text = "Lịch sử khám bệnh",
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("profile") }
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ButtonCustomer(
                        text = "Danh sách bác sĩ",
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("doctor_list") }
                    )
                    ButtonCustomer(
                        text = "Bảo hiểm y tế",
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("profile") }
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ButtonCustomer(
                        text = "Thông tin phòng khám",
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("doctor_list") }
                    )
                    ButtonCustomer(
                        text = "Thông tin cá nhân",
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("profile") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Divider(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                text = "💙 Trung tâm y tế H&D - Sức khỏe là ưu tiên hàng đầu",
                fontSize = 14.sp,
                color = Color(0xFF1565C0),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ButtonCustomer(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4FC3F7), // Màu xanh da trời nhẹ
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(6.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}