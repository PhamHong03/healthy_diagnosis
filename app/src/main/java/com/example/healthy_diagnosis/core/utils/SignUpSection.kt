package com.example.healthy_diagnosis.core.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.R
import com.example.healthy_diagnosis.domain.usecases.register.RegisterRequest
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.ui.theme.LightPink
import com.example.healthy_diagnosis.ui.theme.Roboto

@Composable
fun SignUpSection(
    viewModel: AuthViewModel
) {

    var email by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var role by remember {
        mutableStateOf("account")
    }

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
        textInput = phoneNumber,
        onTextChanged = {phoneNumber = it}
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
    Spacer(modifier = Modifier.height(10.dp))
    RoleSelection()
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            val accountRequest = RegisterRequest(
                email = email,
                password = password,
                username = username,
                phone_number = phoneNumber,
                role = role
            )
            viewModel.registerAccount(accountRequest)
        },
        colors = ButtonDefaults.buttonColors(
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
    uiColor: Color,
    navController: NavController
) {
    val signUpText = "Đăng nhập"
    val fullText = "Bạn đã có tài khoản? $signUpText"

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
                navController.navigate("login")
            }
        }
    )
}

@Composable
fun RoleSelection() {
    var selectedRole by remember { mutableStateOf<String?>(null) }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { selectedRole = "Doctor" }
        ) {
            Checkbox(
                checked = selectedRole == "Doctor",
                onCheckedChange = { selectedRole = "Doctor" }
            )
            Text(
                text = stringResource(id = R.string.roleDoctor),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { selectedRole = "Customer" }
        ) {
            Checkbox(
                checked = selectedRole == "Customer",
                onCheckedChange = { selectedRole = "Customer" }
            )
            Text(
                text = stringResource(id = R.string.roleCustomer),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}
