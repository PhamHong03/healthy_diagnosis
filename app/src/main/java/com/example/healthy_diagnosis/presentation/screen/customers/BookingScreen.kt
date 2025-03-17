package com.example.healthy_diagnosis.presentation.screen.customers

import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthy_diagnosis.core.utils.ButtonClick
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(modifier: Modifier = Modifier) {

    var name by remember {
        mutableStateOf("")
    }
    var birthDate by remember {
        mutableStateOf("")
    }
    var medicalHistory by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
//    var selectedDoctor by remember { mutableStateOf(doctorList.firstOrNull() ?: "") }
//    var selectedRoom by remember { mutableStateOf(roomList.firstOrNull() ?: "") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Đăng ký lịch khám bệnh")})
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DatePickerField(context, "Chọn ngày khám", selectedDate) { selectedDate = it }
//            DropdownMenuField("Chọn bác sĩ", doctorList, selectedDoctor) { selectedDoctor = it }
//            DropdownMenuField("Chọn phòng", roomList, selectedRoom) { selectedRoom = it }
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Họ và tên") },
                modifier = Modifier.fillMaxWidth()
            )
            DatePickerField(context, "Chọn ngày sinh", birthDate) { birthDate = it }
            OutlinedTextField(
                value = medicalHistory,
                onValueChange = { medicalHistory = it },
                label = { Text("Tiền sử bệnh") },
                modifier = Modifier.fillMaxWidth()
            )
            ButtonClick(text = "Đăng ký khám bệnh") {
                
            }
        }
    }
}

@Composable
fun DatePickerField(context: Context, lable: String,date: String, onDateSelected:(String) -> Unit) {
    val calender = Calendar.getInstance()
    val year = calender.get(Calendar.YEAR)
    val month = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DAY_OF_MONTH)

    OutlinedButton(onClick = {
        android.app.DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            onDateSelected("$selectedDay/${selectedMonth + 1}/$selectedYear")
        }, year, month, day).show()
    }, modifier = Modifier.fillMaxWidth()){
        Text(if (date.isEmpty()) lable else date)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label)
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBookingScree() {
    BookingScreen()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDatePickerField() {
    val context = androidx.compose.ui.platform.LocalContext.current
    var selectedDate by remember { mutableStateOf("") }

    DatePickerField(context, "Chọn ngày khám", selectedDate) { selectedDate = it }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDropdownMenuField() {
    val doctorList = listOf("Bác sĩ A", "Bác sĩ B", "Bác sĩ C")
    var selectedDoctor by remember { mutableStateOf(doctorList.first()) }

    DropdownMenuField("Chọn bác sĩ", doctorList, selectedDoctor) { selectedDoctor = it }
}