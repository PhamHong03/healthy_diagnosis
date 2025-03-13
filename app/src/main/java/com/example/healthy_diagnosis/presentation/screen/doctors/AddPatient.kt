package com.example.healthy_diagnosis.presentation.screen.doctors

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.healthy_diagnosis.core.utils.TopBarScreen

@Composable
fun AddPatient(
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
    var status by remember { mutableStateOf("") }

    // Danh sách các biến và label tương ứng
    val fields = listOf(
        "Tên bệnh nhân" to name,
        "Ngày sinh" to dayOfBirth,
        "Giới tính" to gender,
        "Số điện thoại" to phone,
        "Email" to email,
        "Nghề nghiệp" to job,
        "Mã thẻ y tế" to medicalCodeCard,
        "Ngày cấp thẻ" to codeCardDayStart,
        "Trạng thái" to status
    )

    Scaffold(
        topBar = {
            TopBarScreen(
                title = "Thêm bệnh nhân",
                onBackClick = { navController.popBackStack() },
                actionIcon = Icons.Default.Add,
                onActionClick = { navController.navigate("add_patient")}
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            items(fields) { (label, value) ->
                AddPatientItem(
                    label = label,
                    text = value,
                    onValueChange = {
                        when (label) {
                            "Tên bệnh nhân" -> name = it
                            "Ngày sinh" -> dayOfBirth = it
                            "Giới tính" -> gender = it
                            "Số điện thoại" -> phone = it
                            "Email" -> email = it
                            "Nghề nghiệp" -> job = it
                            "Mã thẻ y tế" -> medicalCodeCard = it
                            "Ngày cấp thẻ" -> codeCardDayStart = it
                            "Trạng thái" -> status = it
                        }
                    }
                )
            }
            item {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Thêm bệnh nhân")
                }
            }
        }
    }

}
@Composable
fun AddPatientItem(
    label: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}