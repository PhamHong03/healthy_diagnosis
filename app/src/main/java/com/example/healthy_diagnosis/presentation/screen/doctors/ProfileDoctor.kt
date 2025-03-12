package com.example.healthy_diagnosis.presentation.screen.doctors

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthy_diagnosis.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.example.healthy_diagnosis.core.utils.ConfirmDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileDoctor() {
    var name by remember { mutableStateOf("Nguyễn Văn A") }
    var email by remember { mutableStateOf("nguyenvana@gmail.com") }
    var phone by remember { mutableStateOf("0123 456 789") }
    var specialization by remember { mutableStateOf("Chọn chuyên môn ") }
    var education by remember { mutableStateOf("Chọn trình độ học vấn ") }
    val options = listOf("Ngoại khoa", "Nội khoa", "Chuyên khoa")
    val optionEducation = listOf("Thạc sĩ", "Tiến sĩ", "Giáo sư")
    var showDialog by remember { mutableStateOf(false) }
    var code by remember {
        mutableStateOf("Mã số bác sĩ (121345Edhc)")
    }
    var address by remember {
        mutableStateOf("Nơi làm việc ở .... abcd ....")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.doctor),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        TextButton(onClick = {}) {
            Text(text = "Đổi ảnh đại diện", fontStyle = FontStyle.Italic)
        }
        ProfileItem(value = code, onValueChange = { code = it })
        ProfileItem(value = name, onValueChange = { name = it })
        ProfileItem(value = email, onValueChange = { email = it })
        ProfileItem(value = phone, onValueChange = { phone = it })
        ProfileItem(value = specialization, onValueChange = { specialization = it }, isDropdown = true, options = options)
        ProfileItem(value = education, onValueChange = { education = it }, isDropdown = true, options = optionEducation)
        ProfileItem(value = address, onValueChange = { address = it })
        Spacer(modifier = Modifier.height(10.dp))
        if (showDialog) {
            ConfirmDialog(
                onConfirm = {
                    showDialog = false

                },
                onDismiss = { showDialog = false },
                text = "Bạn có chắc muốn thay đổi không?"
            )
        }

        Button(onClick = { showDialog = true }) {
            Text(text = "Lưu thay đổi")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileItem(
    value: String,
    onValueChange: (String) -> Unit,
    isDropdown: Boolean = false,
    options: List<String> = emptyList()
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
//                .padding(10.dp)
        ) {
            if (isDropdown) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = value,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .background(Color(0xFFFFFFFF))
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    onValueChange(option)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            } else {
                // TextField
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfilePreview(){
    ProfileDoctor()
}