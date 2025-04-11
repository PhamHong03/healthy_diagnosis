package com.example.healthy_diagnosis

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.healthy_diagnosis.presentation.navigation.MyAppNavigation
import com.example.healthy_diagnosis.presentation.viewmodel.ApplicationFormViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.AppointmentFormViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.EducationViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.ImagesViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.MedicalHistoryViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PhysicianViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.RoomViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.SpecializationViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel as viewModels

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val patientViewModel : PatientViewModel by viewModels()
    private val physicianViewModel : PhysicianViewModel by viewModels()
    private val specializationViewModel: SpecializationViewModel by viewModels()
    private val educationViewModel: EducationViewModel by viewModels()
    private val roomViewModel: RoomViewModel by viewModels()
    private val medicalHistoryViewModel: MedicalHistoryViewModel by viewModels()
    private val applicationFormViewModel: ApplicationFormViewModel by viewModels()
    private val appointmentFormViewModel: AppointmentFormViewModel by viewModels()
    private val imagesViewModel: ImagesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppNavigation(
                authViewModel,
                patientViewModel,
                physicianViewModel,
                specializationViewModel,
                educationViewModel,
                roomViewModel,
                medicalHistoryViewModel,
                applicationFormViewModel,
                appointmentFormViewModel,
                imagesViewModel
            )
        }

    }
}
