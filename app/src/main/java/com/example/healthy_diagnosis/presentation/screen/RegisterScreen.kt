package com.example.healthy_diagnosis.presentation.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.core.utils.LoginPrompt
import com.example.healthy_diagnosis.core.utils.SignUpSection
import com.example.healthy_diagnosis.core.utils.TopSection
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
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

            SignUpSection(navController, viewModel)

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
