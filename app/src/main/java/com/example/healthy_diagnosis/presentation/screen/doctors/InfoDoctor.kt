package com.example.healthy_diagnosis.presentation.screen.doctors

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.healthy_diagnosis.core.utils.BannerInfo
import com.example.healthy_diagnosis.core.utils.ButtonClick
import com.example.healthy_diagnosis.core.utils.ConfirmSaveDialog
import com.example.healthy_diagnosis.domain.usecases.physician.PhysicianRequest
import com.example.healthy_diagnosis.presentation.viewmodel.EducationViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PhysicianViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.SpecializationViewModel
import com.example.healthy_diagnosis.ui.theme.BannerColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoDoctor(
    navController: NavController,
    physicianViewModel: PhysicianViewModel,
    specializationViewModel: SpecializationViewModel,
    educationViewModel: EducationViewModel
) {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var specializationId by remember { mutableStateOf(0) }
    var educationId by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        specializationViewModel.fetchSpecializations()
        educationViewModel.fetchEducations()
    }

    val specializations by specializationViewModel.specializationList.collectAsState()
    val educations by educationViewModel.educationList.collectAsState()

    val specializationOptions = specializations.map { it.name }
    val educationOptions = educations.map { it.name }

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BannerInfo(padding = 0.dp)
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
                        text = "THÔNG TIN BÁC SĨ",
                        color = Color.Black,
                        fontSize = 20.sp, textAlign = TextAlign.Center

                    )
                }
            }
            item { DoctorInputField(label = "Họ tên", value = name, onValueChange = { name = it }) }
            item { DoctorInputField(label = "Giới tính", value = gender, onValueChange = { gender = it }) }
            item { DoctorInputField(label = "Số điện thoại", value = phone, onValueChange = { phone = it }) }
            item { DoctorInputField(label = "Email", value = email, onValueChange = { email = it }) }
            item { DoctorInputField(label = "Địa chỉ", value = address, onValueChange = { address = it }) }
            item {
                DoctorDropdownField(
                    label = "Chuyên môn",
                    value = specializations.find { it.id == specializationId }?.name ?: "Chọn chuyên môn",
                    onValueChange = { selected ->
                        specializationId = specializations.find { it.name == selected }?.id ?: 0
                    },
                    options = specializationOptions
                )
            }
            item {
                DoctorDropdownField(
                    label = "Trình độ học vấn",
                    value = educations.find { it.id == educationId }?.name ?: "Chọn trình độ",
                    onValueChange = { selected ->
                        educationId = educations.find { it.name == selected }?.id ?: 0
                    },
                    options = specializationOptions
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
                physicianViewModel.insertPhysician(
                    name = name,
                    email = email,
                    phone = phone,
                    address = address,
                    gender = gender,
                    specializationId = specializationId,
                    educationId = educationId
                )
                showDialog = false
            }
        )
    }
    val isSaved by physicianViewModel.isSaved.collectAsState()

    LaunchedEffect(isSaved) {
        if (isSaved) {
            Toast.makeText(context, "Đăng ký bác sĩ thành công!", Toast.LENGTH_SHORT).show()
            navController.navigate("home") {
                popUpTo("infoDoctor") { inclusive = true }
            }
        }
    }
}

@Composable
fun DoctorInputField(
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
        )
    )
}

@Composable
fun DoctorDropdownField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>
){
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)) {
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
                    text = value.ifEmpty { "Chọn $label" },
                    fontSize = 14.sp,
                    color = if (value.isEmpty()) Color.Blue else Color.Black
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
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
    }
}

@Composable
fun DoctorItem(
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
