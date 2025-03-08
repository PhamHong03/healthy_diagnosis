package com.example.healthy_diagnosis.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.core.utils.LoginPrompt
import com.example.healthy_diagnosis.core.utils.SignUpSection
import com.example.healthy_diagnosis.core.utils.TopSection
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import  androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.Auth


@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
) {
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(authState) {
        authState?.let { result ->
            when {
                result.isSuccess -> {
                    Toast.makeText(context, result.getOrNull(), Toast.LENGTH_SHORT).show()
                    delay(1000)
                    navController.navigate("login")
                }
                result.isFailure -> {
                    Toast.makeText(context, result.exceptionOrNull()?.message ?: "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopSection("ĐĂNG KÝ")
        Spacer(modifier = Modifier.height(20.dp))
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ){

            SignUpSection(viewModel)

            Spacer(modifier = Modifier.height(25.dp))

            val uiColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
            Box(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.8f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ){
                LoginPrompt(uiColor, navController)
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewRegister(){
    val viewModel: AuthViewModel = viewModel()
    RegisterScreen(navController = rememberNavController(), viewModel = viewModel)
}