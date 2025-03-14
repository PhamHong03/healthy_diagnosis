package com.example.healthy_diagnosis.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healthy_diagnosis.core.utils.LoginPrompt
import com.example.healthy_diagnosis.core.utils.SignUpSection
import com.example.healthy_diagnosis.core.utils.TopSection
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import  androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.healthy_diagnosis.R
import com.google.android.gms.auth.api.Auth
import dagger.hilt.android.AndroidEntryPoint


@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(authState) {
        println("Auth State: $authState")
        authState?.let { result ->
            when {
                result.isSuccess -> {
                    println("Đăng ký thành công: ${result.getOrNull()}")
                    Toast.makeText(context, result.getOrNull(), Toast.LENGTH_SHORT).show()
                    delay(1000)
                    navController.navigate("login"){
                        popUpTo("register") { inclusive = true }
                    }
                }
                result.isFailure -> {
                    println("Đăng ký thất bại: ${result.exceptionOrNull()?.message}")
                    Toast.makeText(context, result.exceptionOrNull()?.message ?: "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopSection("ĐĂNG KÝ")
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ){
            SignUpSection(viewModel, navController)
        }
    }
}