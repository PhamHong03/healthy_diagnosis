package com.example.healthy_diagnosis.presentation.screen.doctors


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.healthy_diagnosis.presentation.viewmodel.DiagnosisViewModel
import com.example.healthy_diagnosis.core.utils.TopBarScreen
import com.example.healthy_diagnosis.ui.theme.BannerColor

private const val BASE_URL = "http://192.168.1.9:5000/"
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
            LazyColumn(modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)) {
                item {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = "Số phiếu đăng ký: ${it.application_form_id}",
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Số phiếu khám bênh: ${it.appointment_id}",
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    val hasDisease = !it.category_name.isNullOrBlank()
                    val categoryName = if (hasDisease) it.category_name else "Không có bệnh"
                    val categoryDescription = if (hasDisease) it.category_description ?: "" else ""
                    val diseaseDescription = if (hasDisease && !it.disease_description.isNullOrBlank()) {
                        it.disease_description
                    } else {
                        "Sức khoẻ ổn định"
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    InfoRow("Tên bệnh nhân", it.patient_name.uppercase())
                    Space()
                    InfoRow("Ngày sinh", it.day_of_birth)
                    Space()
                    InfoRow("Số điện thoại", it.phone)
                    Space()
                    InfoRow("Loại bệnh", categoryName)
                    Space()
                    if (categoryDescription.isNotBlank()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.width(180.dp)
                            )
                            Text(
                                text = categoryDescription,
                                fontSize = 16.sp
                            )
                        }
                        Space()
                    }

                    Space()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Tình trạng bệnh: ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.width(180.dp)
                        )
                        Text(
                            text = diseaseDescription,
                            fontSize = 16.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Space()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Hình ảnh siêu âm: ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.width(180.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    it.image_path?.let { relativePath ->
                        val fullImageUrl = BASE_URL + relativePath
                        Log.d("Image URL", "Image full URL: $fullImageUrl")

                        AsyncImage(
                            model = fullImageUrl,
                            contentDescription = "Ảnh kết quả chẩn đoán",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(270.dp)
                                .padding(vertical = 16.dp)
                        )
                    } ?: run {
                        Text(
                            text = "Không có ảnh chẩn đoán",
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        OutlinedButton(
                            onClick = {  },
                            shape = RoundedCornerShape(2.dp),
                            border = BorderStroke(2.dp, BannerColor),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                text = "Thay đổi giá trị",
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(
                            onClick = {  },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BannerColor,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(2.dp),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                                .border(0.dp, BannerColor, RoundedCornerShape(2.dp))
                        ) {
                            Text("Xác nhận kết quả")
                        }
                    }
                }
            }

        } ?: run {
            Text(text = "Đang tải dữ liệu...")
        }
    }
}

@Composable
fun Space() {
    Spacer(modifier = Modifier.height(8.dp))
}
@Composable
fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.width(180.dp)
        )
        Text(
            text = value,
            fontSize = 16.sp
        )
    }
}

