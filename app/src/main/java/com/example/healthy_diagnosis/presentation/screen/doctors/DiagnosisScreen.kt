//package com.example.healthy_diagnosis.presentation.screen.doctors
//
//import android.content.Intent
//import android.net.Uri
//import android.provider.MediaStore
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.compose.rememberAsyncImagePainter
//import com.example.healthy_diagnosis.R
//import com.example.healthy_diagnosis.core.utils.DrawerMenu
//import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
//import androidx.compose.material3.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import com.example.healthy_diagnosis.core.utils.ButtonClick
//import com.example.healthy_diagnosis.core.utils.ConfirmSaveDialog
//import com.example.healthy_diagnosis.data.models.ImagesEntity
//import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormRequest
//import com.example.healthy_diagnosis.presentation.viewmodel.ApplicationFormViewModel
//import com.example.healthy_diagnosis.presentation.viewmodel.AppointmentFormViewModel
//import com.example.healthy_diagnosis.presentation.viewmodel.ImagesViewModel
//import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
//import com.example.healthy_diagnosis.presentation.viewmodel.PhysicianViewModel
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//
//
//val client = OkHttpClient()
//
//fun generateImagePath(physicianId: Int, appointmentId: Int): String {
//    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
//    val currentDate = dateFormat.format(Date())
//    return "IMG_${physicianId}_${appointmentId}_$currentDate.jpg"
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DiagnosisScreen(
//    navController: NavController,
//    authViewModel: AuthViewModel,
//    patientViewModel: PatientViewModel,
//    selectedApplicationFormId: Int?,
//    patientId: Int?,
//    patientName: String?,
//    applicationFormViewModel: ApplicationFormViewModel,
//    appointmentFormViewModel: AppointmentFormViewModel,
//    physicianViewModel : PhysicianViewModel,
//    imagesViewModel: ImagesViewModel
//
//) {
//    val accountId by authViewModel.account.collectAsState()
//    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
//    val notificationCount by remember { mutableStateOf(5) }
//    var selectedPatientId by remember { mutableStateOf<Int?>(null) }
//
//    LaunchedEffect(Unit) {
//        patientViewModel.fetchPatients()
//    }
//    LaunchedEffect(accountId) {
//        accountId?.id?.let { id ->
//            physicianViewModel.fetchPhysicianByAccountId(id)
//        }
//    }
//    val physicianId by physicianViewModel.physicianId.collectAsState()
////    val patients = patient.map{it.id to it.name}
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == android.app.Activity.RESULT_OK) {
//            result.data?.data?.let { uri ->
//                selectedImageUri = uri
//            }
//        }
//    }
//    LaunchedEffect(selectedApplicationFormId) {
//        selectedApplicationFormId?.let {
//            applicationFormViewModel.fetchPatientByApplicationFormId(it)
//        }
//    }
//
////    val patientId by applicationFormViewModel.patientId.observeAsState()
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    var showAddPatientDialog by remember { mutableStateOf(false) }
//
//    var showDialog by remember { mutableStateOf(false) }
//    var description by remember { mutableStateOf("") }
//    var isAppointmentCreated by remember { mutableStateOf(false) }
//    val appointmentId by appointmentFormViewModel.appointmentId.collectAsState()
//    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
//    val currentDate = dateFormat.format(Date())
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth(0.75f)
//                    .background(Color.White)
//            ) {
//                DrawerMenu { selectedItem ->
//                    when (selectedItem) {
//                        "Home" -> navController.navigate("home")
//                        "List" -> navController.navigate("patient_info")
//                        "Diagnosis" -> navController.navigate("diagnosis")
//                    }
//                }
//            }
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                CenterAlignedTopAppBar(
//                    title = { Text("CHẨN ĐOÁN") },
//                    navigationIcon = {
//                        IconButton(onClick = {
//                            scope.launch { drawerState.open() }
//                        }) {
//                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
//                        }
//
//                    }
//                )
//            }
//        ) { paddingValues ->
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row {
//                    Text(
//                        text = "Đơn khám: ",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Text(
//                        text = "${selectedApplicationFormId ?: "Không có"}",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//                Spacer(modifier = Modifier.height(16.dp))
//                Row (
//                    verticalAlignment = Alignment.CenterVertically
//                ){
//                    Text(
//                        text = "Tên bệnh nhân: ",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Text(
//                        text = "${patientName ?: "Không có"}",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                    Spacer(modifier = Modifier.width(50.dp))
//                    IconButton(onClick = {
//                        showDialog = true
//                    }) {
//                        Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = "Tạo phiếu khám"
//                        )
//                    }
//                }
//
//
//                Diagnosis(
//                    onClick = {
//                        val intent = Intent(
//                            Intent.ACTION_PICK,
//                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                        )
//                        launcher.launch(intent)
//                    }
//                )
//
//                Spacer(modifier = Modifier.height(5.dp))
//                Text(
//                    text = stringResource(id = R.string.usecamera),
//                    fontSize = 14.sp, color = Color.Gray,
//                    textAlign = TextAlign.Center
//                )
//                Box(
//                    modifier = Modifier
//                        .padding(20.dp)
//                        .fillMaxWidth()
//                        .height(2.dp)
//                        .background(Color.Black)
//                )
//                selectedImageUri?.let { uri ->
//                    Image(
//                        painter = rememberAsyncImagePainter(uri),
//                        contentDescription = "Chọn ảnh từ thư viện",
//                        modifier = Modifier
//                            .size(400.dp)
//                            .padding(start = 10.dp, end = 10.dp)
//                    )
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                if (selectedImageUri != null) {
//                    Row (
//                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
//                    ) {
//                        ButtonClick(text = "PHÂN TÍCH ẢNH", onClick = {
//                            val imagePath = generateImagePath(
//                                physicianId = physicianId ?: 0,
//                                appointmentId = appointmentId ?: 0
//                            )
//
//                            val imagesEntity = ImagesEntity(
//                                images_path = imagePath,
//                                created_at = currentDate,
//                                physician_id = physicianId ?: 0,
//                                diseases_id = 0,
//                                appointment_id = appointmentId ?: 0
//                            )
//
//                            // Sử dụng selectedImageUri thay cho uri
//                            imagesViewModel.uploadImage(selectedImageUri!!, physicianId ?: 0, appointmentId ?: 0, null) // diseasesId có thể là null
//                        })
//                    }
//                }
//
//            }
//        }
//    }
//
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { showDialog = false },
//            title = { Text("Nhập mô tả phiếu khám") },
//            text = {
//                OutlinedTextField(
//                    value = description,
//                    onValueChange = { description = it },
//                    label = { Text("Mô tả") }
//                )
//            },
//            confirmButton = {
//                Button(onClick = {
//                    selectedApplicationFormId?.let { appId ->
//                        patientId?.let { patId ->
//                            val appointmentForm = AppointmentFormRequest(
//                                description = description,
//                                application_form_id = selectedApplicationFormId
//                            )
//                            appointmentFormViewModel.insertAppointmentForm(appointmentForm)
//                            showDialog = false
//                            description = ""
//                        }
//                    }
//                }) {
//                    Text("OK")
//                }
//            },
//            dismissButton = {
//                Button(onClick = { showDialog = false }) {
//                    Text("Hủy")
//                }
//            }
//        )
//    }
//    if (showAddPatientDialog) {
//        ConfirmSaveDialog(
//            onDismiss = { showAddPatientDialog = false },
//            onConfirm = { showAddPatientDialog = false }
//        )
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SelectedPatient(
//    navController: NavController,
//    patients: List<Pair<Int, String>>,
//    selectedPatientId: Int?,
//    onPatientSelected: (Int) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = it },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        // Hiển thị tên bệnh nhân đã chọn
//        OutlinedTextField(
//            value = patients.find { it.first == selectedPatientId }?.second ?: "Chọn bệnh nhân",
//            onValueChange = {},
//            readOnly = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .menuAnchor(),
//            trailingIcon = {
//                if (selectedPatientId != null) {
//                    Text(
//                        text = "Chi tiết",
//                        color = MaterialTheme.colorScheme.primary,
//                        fontStyle = FontStyle.Italic,
//                        modifier = Modifier.clickable {
//                            navController.navigate("patient_detail/$selectedPatientId")
//                        }
//                    )
//                }
//            }
//        )
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            patients.forEach { (id, name) ->
//                DropdownMenuItem(
//                    text = { Text(name) }, // Hiển thị tên bệnh nhân
//                    onClick = {
//                        onPatientSelected(id)  // Lưu ID khi chọn
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}
//
//
//@Composable
//fun Diagnosis(
//    onClick: () -> Unit
//) {
//    Row (
//    ){
//        Image(
//            modifier = Modifier
//                .padding(top = 30.dp, start = 16.dp, end = 16.dp)
//                .clickable { onClick() }
//                .clip(RoundedCornerShape(40.dp))
//                .size(40.dp),
//            painter = painterResource(id =  R.drawable.upload_image),
//            contentDescription = ""
//        )
//    }
//}




package com.example.healthy_diagnosis.presentation.screen.doctors

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.healthy_diagnosis.R
import com.example.healthy_diagnosis.core.utils.ButtonClick
import com.example.healthy_diagnosis.core.utils.ConfirmSaveDialog
import com.example.healthy_diagnosis.core.utils.DrawerMenu
import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormRequest
import com.example.healthy_diagnosis.presentation.screen.customers.BookingFieldForRoom
import com.example.healthy_diagnosis.presentation.viewmodel.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text


fun generateImagePath(physicianId: Int, appointmentId: Int): String {
    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    return "IMG_${physicianId}_${appointmentId}_$currentDate.jpg"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosisScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    patientViewModel: PatientViewModel,
    selectedApplicationFormId: Int?,
    patientId: Int?,
    patientName: String?,
    applicationFormViewModel: ApplicationFormViewModel,
    appointmentFormViewModel: AppointmentFormViewModel,
    physicianViewModel: PhysicianViewModel,
    imagesViewModel: ImagesViewModel
) {
    val accountId by authViewModel.account.collectAsState()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    val appointmentId by appointmentFormViewModel.appointmentId.collectAsState()
    val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
    val context = LocalContext.current

    val applicationforms by applicationFormViewModel.applicationFormList.collectAsState()
    val applicationformsOptions = applicationforms.map { it.id to it.content }


    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        }
    }

    LaunchedEffect(Unit) {
        patientViewModel.fetchPatients()
    }
    LaunchedEffect(accountId) {
        accountId?.id?.let { physicianViewModel.fetchPhysicianByAccountId(it) }
    }
    val physicianId by physicianViewModel.physicianId.collectAsState()

    LaunchedEffect(selectedApplicationFormId) {
        selectedApplicationFormId?.let { applicationFormViewModel.fetchPatientByApplicationFormId(it) }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .background(Color.White)
            ) {
                DrawerMenu { selectedItem ->
                    when (selectedItem) {
                        "Home" -> navController.navigate("home")
                        "List" -> navController.navigate("patient_info")
                        "Diagnosis" -> navController.navigate("diagnosis")
                    }
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
                Row {

                    Text(
                        text = "Danh sách đơn khám:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        items(applicationformsOptions) { (id, content) ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("ID: $id", fontSize = 16.sp)
                                    Text(content)
                                }
                            }
                        }
                    }



                    Text("Đơn khám: ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("${selectedApplicationFormId ?: "Không có"}", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Tên bệnh nhân: ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("${patientName ?: "Không có"}", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(50.dp))
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Tạo phiếu khám")
                    }
                }
                Diagnosis(
                    onClick = {
                        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Manifest.permission.READ_MEDIA_IMAGES
                        } else {
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        }
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(context, permission) -> {
                                imagePickerLauncher.launch("image/*")
                            }
                            else -> {
                                permissionLauncher.launch(permission)
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.usecamera),
                    fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center
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
                        modifier = Modifier.size(400.dp).padding(horizontal = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (selectedImageUri != null) {
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        ButtonClick(text = "PHÂN TÍCH ẢNH", onClick = {

                            Log.d("Upload", "physicianId=$physicianId, appointmentId=$appointmentId, diseasesId=0")

//                            val imagePath = generateImagePath(
//                                physicianId = physicianId ?: 0,
//                                appointmentId = appointmentId ?: 0
//                            )
//                            val imagesEntity = ImagesEntity(
//                                images_path = imagePath,
//                                created_at = currentDate,
//                                physician_id = physicianId ?: 0,
//                                diseases_id = 0,
//                                appointment_id = appointmentId ?: 0
//                            )
                            imagesViewModel.uploadImage(
                                uri = selectedImageUri!!,
                                physicianId = physicianId ?: 0,
                                appointmentId = appointmentId ?: 0,
                                diseasesId = null
                            )
                        })
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nhập mô tả phiếu khám") },
            text = {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Mô tả") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    selectedApplicationFormId?.let {
                        patientId?.let {
                            appointmentFormViewModel.insertAppointmentForm(
                                AppointmentFormRequest(description, it)
                            )
                            showDialog = false
                            description = ""
                        }
                    }
                }) { Text("OK") }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) { Text("Hủy") }
            }
        )
    }
}

@Composable
fun Diagnosis(onClick: () -> Unit) {
    Row {
        Image(
            modifier = Modifier
                .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                .clickable { onClick() }
                .clip(RoundedCornerShape(40.dp))
                .size(40.dp),
            painter = painterResource(id = R.drawable.upload_image),
            contentDescription = null
        )
    }
}
