package com.example.healthy_diagnosis.presentation.screen.doctors

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.healthy_diagnosis.R
import com.example.healthy_diagnosis.core.utils.DrawerMenu
import com.example.healthy_diagnosis.core.utils.HeaderSection
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.ui.theme.LightPink
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.platform.LocalContext
import com.example.healthy_diagnosis.core.utils.ButtonClick
import com.example.healthy_diagnosis.core.utils.ConfirmSaveDialog
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.presentation.screen.customers.DatePickerFieldCustomer
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.ui.theme.BannerColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosisScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    viewModelPatientViewModel: PatientViewModel
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val notificationCount by remember { mutableStateOf(5) }
    var selectedPatient by remember { mutableStateOf<String?>(null) }
    var patients = listOf("Bệnh nhân A", "Bệnh nhân B", "Bệnh nhân C")

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
            }
        }
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showAddPatientDialog by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu { selectedItem ->
                when (selectedItem) {
                    "Home" -> navController.navigate("home")
                    "List" -> navController.navigate("patient_info")
                    "Diagnosis" -> navController.navigate("diagnosis")
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("CHẨN ĐOÁN") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SelectedPatient(
                    navController = navController,
                    patients = patients,
                    selectedPatient = selectedPatient,
                    onPatientSelected = { selectedPatient = it },
                    onAddPatient = { showAddPatientDialog = true }
                )

                if (showAddPatientDialog) {
                    AddPatientDialog(
                        onDismiss = { showAddPatientDialog = false },
                        onConfirm = { newPatient ->
                            showAddPatientDialog = false
                        } ,
                        viewModel = viewModelPatientViewModel,
                    )
                }

                Diagnosis(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        launcher.launch(intent)
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.usecamera),
                    fontSize = 14.sp, color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.Black)
                )
                selectedImageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Chọn ảnh từ thư viện",
                        modifier = Modifier
                            .size(400.dp)
                            .padding(start = 10.dp, end = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (selectedImageUri != null) {
                    Row (
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    ){
                        ButtonClick(text = "PHÂN TÍCH ẢNH") {

                        }
                    }
                }
            }
        }
    }

    if (showAddPatientDialog) {
        ConfirmSaveDialog(
            onDismiss = { showAddPatientDialog = false },
            onConfirm = { showAddPatientDialog = false }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedPatient(
    navController: NavController,
    patients: List<String>,
    selectedPatient: String?,
    onPatientSelected: (String) -> Unit,
    onAddPatient: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedPatient ?: "Chọn bệnh nhân",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .weight(1f)
                    .menuAnchor(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                patients.forEach { patient ->
                    DropdownMenuItem(
                        text = { Text(patient) },
                        onClick = {
                            onPatientSelected(patient)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        if (selectedPatient == null) {
            IconButton(onClick = { onAddPatient() }) { // ✅ Gọi Dialog thay vì navigate
                Icon(imageVector = Icons.Default.Add, contentDescription = "Thêm bệnh nhân")
            }
        } else {
            Text(
                text = "Chi tiết",
                color = MaterialTheme.colorScheme.primary,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .clickable { navController.navigate("patient_detail/$selectedPatient") }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun AddPatientDialog(
    onDismiss: () -> Unit,
    onConfirm: (PatientEntity) -> Unit,
    viewModel: PatientViewModel
) {
    var name by remember { mutableStateOf("") }
    var dayOfBirth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var job by remember { mutableStateOf("") }
    var medicalCodeCard by remember { mutableStateOf("") }
    var codeCardDayStart by remember { mutableStateOf("") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Thêm Bệnh Nhân",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Họ tên") }, modifier = Modifier.fillMaxWidth())
                DatePickerFieldCustomer(context, "Ngày sinh", dayOfBirth) { dayOfBirth = it }
                OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("Giới tính") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Số điện thoại") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = job, onValueChange = { job = it }, label = { Text("Nghề nghiệp") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = medicalCodeCard, onValueChange = { medicalCodeCard = it }, label = { Text("Mã thẻ y tế") }, modifier = Modifier.fillMaxWidth())
                DatePickerFieldCustomer(context, "Ngày cấp thẻ", codeCardDayStart) { codeCardDayStart = it }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newPatient = PatientEntity(
                        id = 0, // Auto-generate trong Room
                        name = name,
                        day_of_birth = dayOfBirth,
                        gender = gender,
                        phone = phone,
                        email = email,
                        job = job,
                        medical_code_card = medicalCodeCard,
                        code_card_day_start = codeCardDayStart,
                        status = 1
                    )
//                    viewModel.addPatient(newPatient)
                    onConfirm(newPatient)
                },
                colors = ButtonDefaults.buttonColors(containerColor = BannerColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Xác nhận", color = Color.Black)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Hủy")
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}


@Composable
fun Diagnosis(
    onClick: () -> Unit
) {
    Row (
    ){
        Image(
            modifier = Modifier
                .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                .clickable { onClick() }
                .clip(RoundedCornerShape(40.dp))
                .size(40.dp),
            painter = painterResource(id =  R.drawable.upload_image),
            contentDescription = ""
        )
    }
}