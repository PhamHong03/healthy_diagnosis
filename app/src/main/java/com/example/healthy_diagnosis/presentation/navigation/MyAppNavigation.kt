package com.example.healthy_diagnosis.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthy_diagnosis.presentation.screen.LoginScreen
import com.example.healthy_diagnosis.presentation.screen.RegisterScreen
import com.example.healthy_diagnosis.presentation.screen.customers.ApplicationForm
import com.example.healthy_diagnosis.presentation.screen.customers.HomeScreenCustomer
import com.example.healthy_diagnosis.presentation.screen.customers.InfoCustomer
import com.example.healthy_diagnosis.presentation.screen.doctors.DiagnosisScreen
import com.example.healthy_diagnosis.presentation.screen.doctors.Education
import com.example.healthy_diagnosis.presentation.screen.doctors.HomeScreen
import com.example.healthy_diagnosis.presentation.screen.doctors.InfoDoctor
import com.example.healthy_diagnosis.presentation.screen.doctors.Patient
import com.example.healthy_diagnosis.presentation.screen.doctors.ProfileDoctor
import com.example.healthy_diagnosis.presentation.screen.doctors.WorkList
import com.example.healthy_diagnosis.presentation.viewmodel.ApplicationFormViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.EducationViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.MedicalHistoryViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PhysicianViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.RoomViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.SpecializationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyAppNavigation(
    authViewModel: AuthViewModel,
    patientViewModel: PatientViewModel,
    physicianViewModel: PhysicianViewModel,
    specializationViewModel: SpecializationViewModel,
    educationViewModel: EducationViewModel,
    roomViewModel: RoomViewModel,
    medicalHistoryViewModel: MedicalHistoryViewModel,
    applicationFormViewModel: ApplicationFormViewModel
) {
    val navController = rememberNavController()
    val selectPatient by remember {
        mutableStateOf("")
    }
    NavHost(navController = navController, startDestination = "login") {

        composable(route = "signup") {
            RegisterScreen(navController = navController, viewModel = authViewModel)
        }

        composable(route = "login") {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }
        // Doctor screen
        composable(route  = "input_infoDoctor"){
            InfoDoctor(
                navController = navController,
                physicianViewModel = physicianViewModel,
                specializationViewModel = specializationViewModel,
                educationViewModel = educationViewModel,
                authViewModel = authViewModel
            )
        }
        composable(route = "home") {
            HomeScreen(
                navController = navController,
                authViewModel = authViewModel,
                notificationCount = 2,
                physicianViewModel = physicianViewModel
            )
        }
        composable(route = "diagnosis"){
            DiagnosisScreen(navController = navController, authViewModel = authViewModel, patientViewModel)
        }

        composable(route = "healthcare") {
            WorkList(applicationFormViewModel = applicationFormViewModel, patientViewModel = patientViewModel)
        }
        composable(route = "profile") {
            ProfileDoctor()
        }
        composable(route = "patients") {
            Patient(navController = navController, viewModel = patientViewModel)
        }


        //Customer screen
        composable(route = "input_patient" ) {
            InfoCustomer(
                navController = navController,
                patientViewModel = patientViewModel,
                authViewModel = authViewModel,
                onPatientInfoEntered = {},
                medicalHistoryViewModel = medicalHistoryViewModel
            )
        }
        composable(route = "home_patient") {
            HomeScreenCustomer(navController = navController)
        }

         composable(route = "booking") {
            ApplicationForm(
                roomViewModel = roomViewModel,
                medicalHistoryViewModel = medicalHistoryViewModel,
                patientViewModel = patientViewModel,
                navController = navController,
                authViewModel = authViewModel,
                applicationViewModel = applicationFormViewModel
            )
        }

        composable(route = "patient_detail/selectedPatient"){

        }

    }
}
