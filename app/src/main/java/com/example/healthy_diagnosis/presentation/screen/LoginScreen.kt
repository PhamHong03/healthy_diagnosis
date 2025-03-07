package com.example.healthy_diagnosis.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.core.utils.SocialMediaSection
import com.example.healthy_diagnosis.core.utils.TextFieldLoginRegister
import com.example.healthy_diagnosis.core.utils.TopSection
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.ui.theme.LightPink
import com.example.healthy_diagnosis.ui.theme.Roboto
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopSection("ĐĂNG NHẬP")
        Spacer(modifier = Modifier.height(30.dp))
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ){

            LoginSection(navController, viewModel)
            Spacer(modifier = Modifier.height(25.dp))
            SocialMediaSection()
            val uiColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.8f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ){
                SignUpPromt(uiColor)
            }
        }
    }
}

@Composable
fun SignUpPromt(uiColor: Color) {
    val signUpText = "Đăng ký"
    val fullText = "Bạn chưa có tài khoản? $signUpText"


    val signupStartIndex = fullText.indexOf((signUpText))

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color(0xFF94A3B8),
                fontSize = 14.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Bạn chưa có tài khoản? ")
        }
        withStyle(
            style = SpanStyle(
                color = uiColor,
                fontSize = 14.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(signUpText)
        }
    }
    ClickableText(
        text = annotatedString,
        onClick =
        { offset ->
            if (offset in signupStartIndex until (signupStartIndex + signUpText.length)) {
//                navController.navigate("signup")
            }
        }
    )
}


@Composable
fun LoginSection(
    navController: NavController,
    viewModel: AuthViewModel
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoginTriggered by remember { mutableStateOf(false) }

//    val loginState by viewModel.loginResult.collectAsState()
    val context = LocalContext.current
//    val authState = authViewModel.authState.observeAsState()

    TextFieldLoginRegister(
        label = "Email",
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Toggle visibility"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        textInput = email,
        onTextChanged = {email = it}
    )
    Spacer(modifier = Modifier.height(15.dp))
    TextFieldLoginRegister(
        label = "Mật khẩu",
        trailingIcon = {
            Icon(
                imageVector = if (isPasswordVisible) Icons.Filled.Done else Icons.Filled.Lock,
                contentDescription = if (isPasswordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu",
                modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
            )
        },
        modifier = Modifier.fillMaxWidth(),
        textInput = password,
        onTextChanged = { password = it },
        isPasswordField = true,
        isPasswordVisible = isPasswordVisible
    )
    Spacer(modifier = Modifier.height(7.dp))
    Box(
        modifier = Modifier,
    ){
        TextButton(onClick = {}) {
            Text(
                text = "Quên mật khẩu?",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
    Spacer(modifier = Modifier.height(7.dp))

//    LaunchedEffect(authState.value) {
//        if (authState.value is AuthState.Authenticated &&
//            navController.currentDestination?.route != "select_role"
//        ) {
//            navController.navigate("select_role") {
//                popUpTo("login") { inclusive = true }
//            }
//        }
//    }

//    loginState?.let {
//        when {
//            it.isSuccess -> {
//                LaunchedEffect(Unit) {
//                    navController.navigate("home") {
//                        popUpTo("login")
//                    }
//                }
//            }
//            it.isFailure -> {
//                Text(text = "Đăng nhập thất bại:: ${it.exceptionOrNull()?.message}")
//            }
//        }
//    }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
//            isLoginTriggered = true
//            authViewModel.login(email, password)
        },
//        enabled = authState.value != AuthState.Loading,
        colors = ButtonDefaults.buttonColors(
//            containerColor = if (isSystemInDarkTheme()) BlueGray else Black,
            containerColor = LightPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 5.dp)
    ) {
        Text(
            text = "ĐĂNG NHẬP",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewLogin(){
//    LoginScreen()
}