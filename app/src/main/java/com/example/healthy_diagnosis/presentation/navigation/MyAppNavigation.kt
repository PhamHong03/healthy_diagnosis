package com.example.healthy_diagnosis.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthy_diagnosis.presentation.screen.LoginScreen
import com.example.healthy_diagnosis.presentation.screen.RegisterScreen
import com.example.healthy_diagnosis.presentation.screen.doctors.DiagnosisScreen
import com.example.healthy_diagnosis.presentation.screen.doctors.Education
import com.example.healthy_diagnosis.presentation.screen.doctors.HomeScreen
import com.example.healthy_diagnosis.presentation.screen.doctors.ProfileDoctor
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyAppNavigation(
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {

        composable(route = "signup") {
            RegisterScreen(navController = navController, viewModel = authViewModel)
        }

        composable(route = "login") {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }

        composable(route = "home") {
            HomeScreen(
                navController = navController,
                authViewModel = authViewModel,
                notificationCount = 2
            )
        }
        composable(route = "diagnosis"){
            DiagnosisScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = "healthcare") {
            Education()
        }
        composable(route = "profile") {
            ProfileDoctor()
        }
    }
}
