package com.example.healthy_diagnosis.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthy_diagnosis.presentation.screen.LoginScreen
import com.example.healthy_diagnosis.presentation.screen.RegisterScreen
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "signup") {

        composable(route = "signup") {
            val authViewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(navController = navController, viewModel = authViewModel)
        }

        composable(route = "login") {
            val authViewModel: AuthViewModel = hiltViewModel()
            LoginScreen(navController = navController, viewModel = authViewModel)
        }
    }
}
