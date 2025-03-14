package com.example.healthy_diagnosis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.healthy_diagnosis.presentation.navigation.MyAppNavigation
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.EducationViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PhysicianViewModel
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppNavigation(authViewModel,patientViewModel, physicianViewModel, specializationViewModel, educationViewModel)
        }
    }
}