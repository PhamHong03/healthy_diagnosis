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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.healthy_diagnosis.core.utils.ButtonClick
import com.example.healthy_diagnosis.core.utils.ConfirmSaveDialog
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.presentation.screen.customers.DatePickerFieldCustomer
import com.example.healthy_diagnosis.presentation.viewmodel.ApplicationFormViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.ui.theme.BannerColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosisScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    patientViewModel: PatientViewModel,
    selectedApplicationFormId: Int?,
    patientId: Int?,
    patientName: String?,
    applicationFormViewModel: ApplicationFormViewModel

) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val notificationCount by remember { mutableStateOf(5) }
    var selectedPatientId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        patientViewModel.fetchPatients()
    }


//    val patients = patient.map{it.id to it.name}

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
            }
        }
    }
    LaunchedEffect(selectedApplicationFormId) {
        selectedApplicationFormId?.let {
            applicationFormViewModel.fetchPatientByApplicationFormId(it)
        }
    }

//    val patientId by applicationFormViewModel.patientId.observeAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showAddPatientDialog by remember { mutableStateOf(false) }

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
                        IconButton(onClick = {
                            scope.launch { drawerState.open() } // Mở lại Drawer khi click
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }

                    }
                )
            }
        ) { paddingValues ->
            Row (modifier = Modifier.padding(paddingValues).padding(start = 20.dp).fillMaxSize()){

                Column (){
                    Text(
                        text = "Chẩn đoán bệnh gan",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
//                Text(text = "Mã đơn khám: ${selectedApplicationFormId ?: "Không có"}")
//                Text(text = "Mã đơn khám: ${patientId ?: "Không có"}")
                    Text(
                        text = "Tên bệnh nhân: ${patientName ?: "Không có"}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {



//                SelectedPatient(
//                    navController = navController,
//                    patients = patients,
//                    selectedPatientId = selectedPatientId,
//                    onPatientSelected = { id -> selectedPatientId = id }
//                )

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
                        ButtonClick(text = "PHÂN TÍCH ẢNH", onClick = {})
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
    patients: List<Pair<Int, String>>,
    selectedPatientId: Int?,
    onPatientSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Hiển thị tên bệnh nhân đã chọn
        OutlinedTextField(
            value = patients.find { it.first == selectedPatientId }?.second ?: "Chọn bệnh nhân",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            trailingIcon = {
                if (selectedPatientId != null) {
                    Text(
                        text = "Chi tiết",
                        color = MaterialTheme.colorScheme.primary,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.clickable {
                            navController.navigate("patient_detail/$selectedPatientId")
                        }
                    )
                }
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            patients.forEach { (id, name) ->
                DropdownMenuItem(
                    text = { Text(name) }, // Hiển thị tên bệnh nhân
                    onClick = {
                        onPatientSelected(id)  // Lưu ID khi chọn
                        expanded = false
                    }
                )
            }
        }
    }
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