package com.example.healthy_diagnosis.presentation.screen.customers

import android.app.DatePickerDialog
import android.content.Context
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
import java.util.Calendar

@Composable
fun InfoCustomer(
    navController: NavController
){
    var name by remember { mutableStateOf("") }
    var dayOfBirth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var job by remember { mutableStateOf("") }
    var medicalCodeCard by remember { mutableStateOf("") }
    var codeCardDayStart by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(-1) }

    val context = LocalContext.current
    val statusOptions = listOf(
        0 to "Không còn sử dụng",
        1 to "Còn sử dụng"
    )

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BannerInfo(padding = 0.dp, "Trung tâm khám chữa bệnh")
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                Row (
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        text = "THÔNG TIN CỦA BẠN",
                        color = Color.Black,
                        fontSize = 20.sp, textAlign = TextAlign.Center

                    )
                }
            }
            item { CustomerInput(
                label = "Họ tên",
                value = name,
                onValueChange = { name = it }
            ) }
            item {
                DatePickerFieldCustomer(context, "Chọn ngày sinh", dayOfBirth) { dayOfBirth = it }
            }
            item { CustomerInput(
                label = "Giới tính",
                value = gender,
                onValueChange = { gender = it }
            ) }
            item { CustomerInput(
                label = "Số điện thoại",
                value = phone,
                onValueChange = { phone = it }
            ) }
            item { CustomerInput(
                label = "Email",
                value = email,
                onValueChange = { email = it }
            ) }
            item { CustomerInput(
                label = "Công việc",
                value = job,
                onValueChange = { job = it }
            ) }

            item { CustomerInput(
                label = "Mã thẻ khám bệnh",
                value = medicalCodeCard,
                onValueChange = { medicalCodeCard = it }
            ) }

            item {
                DatePickerFieldCustomer(context, "Ngày bắt đầu khám", codeCardDayStart) { codeCardDayStart = it }
            }

            item {
                CustomerDropdownField(
                    label = "Tình trạng",
                    value = status,
                    onValueChange = { status = it },
                    options = statusOptions
                )
            }
            item{
                ButtonClick(text = "Lưu thông tin", onClick = {
                    showDialog = true
                })
            }
        }
    }
    if (showDialog) {
        ConfirmSaveDialog(
            onDismiss = { showDialog = false },
            onConfirm = {

                showDialog = false
            }
        )
    }
//    val isSaved by physicianViewModel.isSaved.collectAsState()
//
//    LaunchedEffect(isSaved) {
//        if (isSaved) {
//            Toast.makeText(context, "Đăng ký bác sĩ thành công!", Toast.LENGTH_SHORT).show()
//            navController.navigate("home") {
//                popUpTo("infoDoctor") { inclusive = true }
//            }
//        }
//    }
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
            onDateSelected("$selectedDay/${selectedMonth + 1}/$selectedYear")
        },
        year, month, day
    )

    OutlinedTextField(
        value = date,
        onValueChange = {}, // Không cho phép nhập bằng tay
        label = { Text(label) },
        readOnly = true, // Chặn nhập trực tiếp
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Pick Date")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() } // Nhấn vào để mở DatePicker
    )
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


@Composable
fun CustomerItem(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isDropdown: Boolean = false,
    options: List<String> = emptyList()
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.Black)

        if (isDropdown) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFECF5FF))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = value.ifEmpty { "Select from the list" },
                        fontSize = 14.sp,
                        color = if (value.isEmpty()) Color(0xFF007AFF) else Color.Black
                    )
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        } else {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = label)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                    disabledContainerColor = Color(0xFFF5F5F5),
                    disabledTextColor = Color.Gray
                ),
                enabled = false
            )
        }
    }
}
