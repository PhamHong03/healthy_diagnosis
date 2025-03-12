package com.example.healthy_diagnosis.presentation.screen.doctors

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.EducationViewModel

@Composable
fun Education(
    viewModel: EducationViewModel = hiltViewModel()
) {
    var education by remember {
        mutableStateOf("")
    }
    val educationList by viewModel.educationList.collectAsState()

    val context = LocalContext.current

    val addEducationResult by viewModel.addEducationResult.collectAsState()

    LaunchedEffect(addEducationResult) {
        addEducationResult?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearAddEducationResult()
        }
    }

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Thêm trình độ học vấn")

        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = education,
            onValueChange = {
                education = it
            },
            label = { Text(text = "Nhập trình độ")}
        )

        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Button(onClick = { viewModel.addEducation(education) }) {
                Text(text = "Thêm")
            }

            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {
                if (educationList.isNotEmpty()) {
                    viewModel.deleteEducation(educationList.first().id)
                }
            }) {
                Text(text = "Xóa")
            }

            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { viewModel.fetchEducations() }) {
                Text(text = "Danh sách")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Column {
            educationList.forEach{ edu ->
                Text(text = edu.name)
            }
        }
    }
}