package com.example.healthy_diagnosis.presentation.screen.customers

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.core.utils.BannerInfo
import com.example.healthy_diagnosis.core.utils.ButtonClick
import com.example.healthy_diagnosis.core.utils.ConfirmSaveDialog
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.MedicalHistoryViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
@Composable
fun InfoCustomer(
    navController: NavController,
    patientViewModel: PatientViewModel,
    onPatientInfoEntered: (Boolean) -> Unit,
    authViewModel: AuthViewModel,
    medicalHistoryViewModel: MedicalHistoryViewModel
) {
    val context = LocalContext.current
    val account by authViewModel.account.collectAsState()
    val accountId = account?.id ?: -1


    var name by remember { mutableStateOf("") }
    var day_of_birth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var job by remember { mutableStateOf("") }
    var medical_code_card by remember { mutableStateOf("") }
    var code_card_day_start by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(-1) }
    val statusOptions = listOf(
        0 to "Không còn sử dụng",
        1 to "Còn sử dụng"
    )
    var isPatientInfoEntered by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

//    LaunchedEffect(isPatientInfoEntered) {
//        if (isPatientInfoEntered) {
//            medicalHistoryViewModel.fetchMedicalHistory() // ✅ Cập nhật danh sách lịch sử khám ngay lập tức
//        }
//    }


    Scaffold(
        topBar = {
//            BannerInfo(padding = 0.dp, "Trung tâm khám chữa bệnh")
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item { Text("THÔNG TIN CỦA BẠN", fontSize = 20.sp, textAlign = TextAlign.Center) }
            item { CustomerInput(label = "Họ tên", value = name, onValueChange = { name = it }) }
            item { DatePickerFieldCustomer(context, "Chọn ngày sinh", day_of_birth) { day_of_birth = it } }
            item { CustomerInput(label = "Giới tính", value = gender, onValueChange = { gender = it }) }
            item { CustomerInput(label = "Số điện thoại", value = phone, onValueChange = { phone = it }) }
            item { CustomerInput(label = "Email", value = email, onValueChange = { email = it }) }
            item { CustomerInput(label = "Công việc", value = job, onValueChange = { job = it }) }
            item { CustomerInput(label = "Mã thẻ khám bệnh", value = medical_code_card, onValueChange = { medical_code_card = it }) }
            item { DatePickerFieldCustomer(context, "Ngày bắt đầu khám", code_card_day_start) { code_card_day_start = it } }
            item {
                CustomerDropdownField(
                    label = "Tình trạng",
                    value = status,
                    onValueChange = { status = it },
                    options = statusOptions
                )
            }
            item {
                ButtonClick(text = "Xác nhận thông tin", onClick = {
                    showDialog = true
                    onPatientInfoEntered(true)
                })
            }
        }
    }

    if (showDialog) {
        ConfirmSaveDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                patientViewModel.insertPatient(
                    account_id = accountId,
                    name = name,
                    day_of_birth = day_of_birth,
                    gender = gender,
                    phone = phone,
                    email = email,
                    job = job,
                    medical_code_card = medical_code_card,
                    code_card_day_start = code_card_day_start,
                    status = status
                )
                showDialog = false
            }
        )
    }
}

@Composable
fun DatePickerFieldCustomer(context: Context, label: String, date: String, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(formattedDate)
        },
        year, month, day
    )

    OutlinedTextField(
        value = if (date.isNotEmpty()) formatDateToDisplay(date) else "",
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Pick Date")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() }
    )
}

fun formatDateToDisplay(date: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val parsedDate = inputFormat.parse(date)
        parsedDate?.let { outputFormat.format(it) } ?: date
    } catch (e: Exception) {
        date
    }
}


@Composable
fun CustomerInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        ),
        enabled = true
    )
}
@Composable
fun CustomerDropdownField(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    options: List<Pair<Int, String>>
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
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD0E8FF)) // Xanh nhạt
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = options.firstOrNull { it.first == value }?.second ?: "Chọn $label",
                    fontSize = 14.sp,
                    color = if (value == -1) Color.Blue else Color.Black
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { (intValue, stringValue) ->
                DropdownMenuItem(
                    text = { Text(text = stringValue) },
                    onClick = {
                        onValueChange(intValue)
                        expanded = false
                    }
                )
            }
        }
    }
}
