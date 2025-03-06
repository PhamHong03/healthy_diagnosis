package com.example.healthy_diagnosis.core.utils

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.ui.theme.Black
import com.example.healthy_diagnosis.ui.theme.BlueGray
import com.example.healthy_diagnosis.ui.theme.LightPink
import com.example.healthy_diagnosis.ui.theme.Roboto

@Composable
fun SignUpSection(

) {

//    val authState by authViewModel.authState.observeAsState()

    var email by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
//    LaunchedEffect(authState) {
//        when (authState) {
//            is AuthState.Authenticated -> {
//                navController.navigate("login")
//            }
//            is AuthState.Error -> {
//                val errorMessage = (authState as AuthState.Error).message
//                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
//            }
//            else -> Unit
//        }
//    }

    TextFieldLoginRegister(
        label = "Tên của bạn",
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Toggle visibility"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        textInput = username,
        onTextChanged = {username = it}
    )
    Spacer(modifier = Modifier.height(10.dp))
    TextFieldLoginRegister(
        label = "Số điện thoại",
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = "Toggle visibility"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        textInput = phone,
        onTextChanged = {phone = it}
    )
    Spacer(modifier = Modifier.height(10.dp))
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
    Spacer(modifier = Modifier.height(10.dp))
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
    Spacer(modifier = Modifier.height(30.dp))
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
//            authViewModel.signup(email,password,username, phone)
        },
        colors = ButtonDefaults.buttonColors(
//            containerColor = if (isSystemInDarkTheme()) BlueGray else Black,
            containerColor = LightPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = 5.dp)
    ) {
        Text(
            text = "ĐĂNG KÝ",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun LoginPrompt(
    uiColor: Color
) {
    val signUpText = "Đăng nhập"
    val fullText = "Bạn đã có tài khoản? $signUpText"

    // xac dinh vi tri bat dau cua dang ky
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
            append("Bạn đã có tài khoản? ")
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
        onClick = { offset ->
            if (offset in signupStartIndex until (signupStartIndex + signUpText.length)) {
//                navController.navigate("login")
            }
        }
    )
}