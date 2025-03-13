package com.example.healthy_diagnosis.presentation.screen.doctors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import androidx.compose.material3.Text
import com.example.healthy_diagnosis.core.utils.PatientItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun Patient(
    navController: NavController,
    viewModel: PatientViewModel
) {
    val patientList by viewModel.patientList.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { navController.navigate("add_patient") },  // Chuyển đến màn hình thêm bệnh nhân
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Thêm bệnh nhân")
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(patientList) { patient ->
                PatientItem(
                    patient = patient,
                    onDetailClick = { navController.navigate("patient_detail/${patient.id}") },
                    onDeleteClick = {
                        viewModel.deletePatient(patient.id)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}