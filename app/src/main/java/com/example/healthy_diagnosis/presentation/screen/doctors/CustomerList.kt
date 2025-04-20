package com.example.healthy_diagnosis.presentation.screen.doctors


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.core.utils.PatientItem
import com.example.healthy_diagnosis.core.utils.TopBarScreen
import com.example.healthy_diagnosis.presentation.viewmodel.ApplicationFormViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.AppointmentFormViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.CategoryDiseaseViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.DiseaseViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.ImagesViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.MedicalHistoryViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PhysicianViewModel


@Composable
fun Patient(
    navController: NavController,
    physicianViewModel: PhysicianViewModel,
    viewModel: PatientViewModel ,
    appointmentFormViewModel : AppointmentFormViewModel,
    medicalHistoryViewModel: MedicalHistoryViewModel,
    patientViewModel: PatientViewModel,
    categoryDiseaseViewModel: CategoryDiseaseViewModel,
    diseaseViewModel: DiseaseViewModel,
    applicationFormViewModel: ApplicationFormViewModel,
    imagesViewModel: ImagesViewModel
) {
    val physicianId by physicianViewModel.physicianId.collectAsState()
    val patients by physicianViewModel.patients.observeAsState(emptyList())
    val uniquePatients = patients.distinctBy { it.patient_id }

    LaunchedEffect(physicianId) {
        physicianId?.let { id ->
            physicianViewModel.fetchPatients(id)
        }
    }
    LaunchedEffect(Unit) {
        medicalHistoryViewModel.fetchMedicalHistory()
        patientViewModel.fetchPatients()
        applicationFormViewModel.fetchApplicationForm()
        categoryDiseaseViewModel.fetchCategoryDisease()
        diseaseViewModel.fetchDisease()
        appointmentFormViewModel.fetchAppointmentForm()
        imagesViewModel.fetchImages()
    }
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
                .padding(8.dp)
        ) {
            LazyColumn {
                items(uniquePatients) { patient ->
                    PatientItem(
                        patient = patient,
                        onDetailClick = {
                            navController.navigate("patient_detail/${patient.patient_id}")
                        },
                        onDeleteClick = {
                            viewModel.deletePatient(patient.patient_id)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
