package com.example.healthy_diagnosis.presentation.screen.doctors


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.core.utils.PatientItem
import com.example.healthy_diagnosis.core.utils.TopBarScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Patient(
    navController: NavController,
    viewModel: PatientViewModel
) {
    val patientList by viewModel.patientList.collectAsState()
    var showDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopBarScreen(
                title = "Danh sách bệnh nhân",
                onBackClick = { navController.popBackStack() },
                actionIcon = Icons.Default.Add,
                onActionClick = { navController.navigate("add_patient")}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(patientList) { patient ->
                    PatientItem(
                        patient = patient,
                        onDetailClick = { navController.navigate("patient_detail/${patient.id}") },
                        onDeleteClick = { viewModel.deletePatient(patient.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        if(showDialog) {

        }
    }
}
