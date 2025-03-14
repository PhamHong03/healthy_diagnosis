package com.example.healthy_diagnosis.presentation.screen.doctors


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.core.utils.PatientItem
import com.example.healthy_diagnosis.core.utils.TopBarScreen


@Composable
fun Patient(
    navController: NavController,
    viewModel: PatientViewModel
) {
    val patientList by viewModel.patientList.collectAsState()
    Scaffold(
        topBar = {
            TopBarScreen(
                title = "Danh sách bệnh nhân",
                onBackClick = { navController.popBackStack() }
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
    }
}
