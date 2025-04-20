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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.healthy_diagnosis.R
import com.example.healthy_diagnosis.core.utils.ButtonClick
import com.example.healthy_diagnosis.core.utils.DrawerMenu
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormRequest
import com.example.healthy_diagnosis.presentation.viewmodel.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.Text
import com.example.healthy_diagnosis.core.utils.ConfirmSaveDialog
import androidx.compose.material3.CircularProgressIndicator
import kotlinx.coroutines.delay


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
    imagesViewModel: ImagesViewModel,
    medicalHistoryViewModel: MedicalHistoryViewModel,
    categoryDiseaseViewModel: CategoryDiseaseViewModel,
    diseaseViewModel: DiseaseViewModel
) {
    val accountId by authViewModel.account.collectAsState()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("Tạo phiếu khám ") }
    val appointmentId by appointmentFormViewModel.appointmentId.collectAsState()
    val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            Log.d("ImagePicker", "Image URI selected: $uri")
            selectedImageUri = uri
        } else {
            Log.d("ImagePicker", "No image selected")
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        } else {
            Log.e("Permission", "Permission denied")
        }
    }
    LaunchedEffect(Unit) {
        medicalHistoryViewModel.fetchMedicalHistory()
        patientViewModel.fetchPatients()
//        applicationFormViewModel.fetchApplicationForm()
        categoryDiseaseViewModel.fetchCategoryDisease()
        diseaseViewModel.fetchDisease()
    }
    LaunchedEffect(accountId) {
        accountId?.id?.let { physicianViewModel.fetchPhysicianByAccountId(it) }
    }
    val physicianId by physicianViewModel.physicianId.collectAsState()

    LaunchedEffect(selectedApplicationFormId) {
        selectedApplicationFormId?.let {
            applicationFormViewModel.fetchApplicationForm()
            applicationFormViewModel.fetchPatientByApplicationFormId(it)
//            diseaseViewModel.fetchDisease()
        }

    }
    LaunchedEffect(appointmentId) {
        if (appointmentId != null) {
            Log.d("AppointmentForm", "New Appointment ID: $appointmentId")
        }
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
                        "Healthcare" -> navController.navigate("healthcare")
                        "Patients" -> navController.navigate("patients")

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
                if (selectedImageUri != null && appointmentId != null) {
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        ButtonClick(
                            text = "PHÂN TÍCH ẢNH",
                            onClick = {
                                Log.d("UploadImage", "URI: $selectedImageUri")
                                Log.d("Upload", "physicianId=$physicianId, appointmentId=$appointmentId, diseasesId=0")
                                imagesViewModel.uploadImage(
                                    uri = selectedImageUri!!,
                                    physicianId = physicianId ?: 0,
                                    appointmentId = appointmentId ?: 0,
                                    diseasesId = null
                                ){ result ->
                                    if (result) {
                                        navController.navigate("result_screen/${appointmentId}")
                                    } else {

                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        ConfirmSaveDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                selectedApplicationFormId?.let { appFormId ->
                    patientId?.let { patId ->
                        appointmentFormViewModel.insertAppointmentForm(
                            AppointmentFormRequest(description, appFormId)
                        )
                        showDialog = false
                        description = "Tạo phiếu khám"
                    }
                }
                showDialog = false
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
