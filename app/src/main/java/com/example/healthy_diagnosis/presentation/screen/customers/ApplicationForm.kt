package com.example.healthy_diagnosis.presentation.screen.customers

import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.core.utils.BannerInfo
import com.example.healthy_diagnosis.core.utils.ButtonClick
import com.example.healthy_diagnosis.core.utils.TopBarScreen
import com.example.healthy_diagnosis.data.models.MedicalHistoryEntity
import com.example.healthy_diagnosis.presentation.screen.doctors.DoctorDropdownField
import com.example.healthy_diagnosis.presentation.viewmodel.ApplicationFormViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.MedicalHistoryViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationForm(
    roomViewModel: RoomViewModel,
    medicalHistoryViewModel: MedicalHistoryViewModel,
    patientViewModel: PatientViewModel,
    navController: NavController,
    authViewModel: AuthViewModel,
    applicationViewModel: ApplicationFormViewModel,
    patientId: Int?
) {
    val currentUser by authViewModel.account.collectAsState()
    val accountId = currentUser?.id ?:0
    var content by remember {mutableStateOf("")}
    var date by remember { mutableStateOf("") }
    var roomId by remember { mutableStateOf("") }
    var medicalHistoryId by remember { mutableStateOf(0) }
    var isPatientInfoEntered by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isSaved by applicationViewModel.isSave.collectAsState()

    val rooms by roomViewModel.roomList.collectAsState()
    val roomOptions = rooms.map { it.id to it.name }

    val medicalHistories by medicalHistoryViewModel.medicalHistoryList.collectAsState()
    val filteredMedicalHistories = medicalHistories.filter { it.calendar_date == date }

    val medicalHistoriesOption = filteredMedicalHistories.map { it.id to it.physician_name }

    LaunchedEffect(accountId) {
        if (accountId != 0) {
            patientViewModel.fetchPatientIdByAccountId(accountId)
        }
    }

    LaunchedEffect(Unit) {
        roomViewModel.fetchRoom()
        medicalHistoryViewModel.fetchMedicalHistory()
    }
    LaunchedEffect(isSaved) {
        if (isSaved) {
            Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            navController.navigate("home_patient")
        }
    }
    LaunchedEffect(isSaved) {
        Log.d("ApplicationForm", "isSaved state: $isSaved")
        if (isSaved) {
            Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            navController.navigate("home_patient")
        }
    }
    Scaffold(
        topBar = { TopBarScreen(title = "Đặt lịch khám bệnh", onBackClick = { navController.popBackStack()}) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {

            Box(modifier = Modifier.weight(1f)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxSize()
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
                    Spacer(modifier = Modifier.height(10.dp))
                    DatePickerFieldCustomer(context, "Chọn ngày khám", date) { date = it }
                    BookingFieldForRoom(
                        label = "Phòng",
                        selectedValue = roomId,
                        onValueChange = { roomId = it },
                        options = roomOptions
                    )

                    if (patientId != null && patientId != 0) {
                        DoctorDropdownField(
                            label = "Chọn bác sĩ khám khám",
                            selectedId = medicalHistoryId,
                            onValueChange = { selectedMedicalHistory ->
                                medicalHistoryId = selectedMedicalHistory
                            },
                            options = medicalHistoriesOption
                        )
                    } else {

                        Text(
                            text = "Lần đầu khám, vui lòng nhập thông tin bệnh nhân!",
                            color = Color.Red,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        InfoCustomer(
                            patientViewModel = patientViewModel,
                            navController = navController,
                            onPatientInfoEntered = { isPatientInfoEntered = it },
                            authViewModel = authViewModel,
                            medicalHistoryViewModel = medicalHistoryViewModel
                        )

                    }
                    Space()
                    CustomerInput(
                        label = "Mô tả triệu chứng của bạn",
                        value = content,
                        onValueChange = {
                            content = it
                        }
                    )
                }
            }

            ButtonClick(
                text = "Đăng ký khám bệnh",
                onClick = {
                    if (patientId == 0) {
                        Toast.makeText(context, "Vui lòng nhập thông tin bệnh nhân!", Toast.LENGTH_SHORT).show()
                    } else {
                        applicationViewModel.insertApplicationForm(
                            content = content,
                            application_form_date = date,
                            patient_id = patientId ?: 0,
                            room_id = roomId,
                            medical_history_id = medicalHistoryId
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun BookingFieldForRoom(
    label: String,
    selectedValue: String,
    onValueChange: (String) -> Unit,
    options: List<Pair<String, String>>
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD0E8FF))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = options.find { it.first == selectedValue }?.second ?: "Chọn $label",
                    fontSize = 14.sp,
                    color = if (selectedValue.isEmpty()) Color.Blue else Color.Black
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (id, name) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        onValueChange(id)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Space(){
    Spacer(modifier = Modifier.height(16.dp))
}