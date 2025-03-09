package com.example.healthy_diagnosis.presentation.screen.doctors

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.healthy_diagnosis.R
import com.example.healthy_diagnosis.core.utils.DrawerMenu
import com.example.healthy_diagnosis.core.utils.HeaderSection
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.ui.theme.LightPink

@Composable
fun DiagnosisScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val notificationCount by remember {
        mutableStateOf(5)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
            }
        }
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu { selectedItem ->
                when (selectedItem) {
                    "Home" -> {navController.navigate("home")}
                    "List" -> {navController.navigate("patient_info")}
                    "Diagnosis" -> { navController.navigate("diagnosis")}
                    "CSSK" -> { }
                    "Result" -> { }
                    "HoiChuan" -> { }
                    "Personal" -> { }
                    "Setting" -> { }
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(10.dp)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderSection(authViewModel, notificationCount, "Hello Dr.")
                Diagnosis(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        launcher.launch(intent)
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.usecamera),
                    fontSize = 14.sp, color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(2.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .background(Color.Black)
                )
                selectedImageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Chọn ảnh từ thư viện",
                        modifier = Modifier.size(400.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (selectedImageUri != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2386B8),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(size = 5.dp)
                        ) {
                            Text(
                                text = "PHÂN TÍCH ẢNH",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Diagnosis(
    onClick: () -> Unit
) {
    Row {
        Image(
            modifier = Modifier
                .padding(top = 30.dp)
                .clickable { onClick() }
                .clip(RoundedCornerShape(40.dp))
                .size(40.dp),
            painter = painterResource(id =  R.drawable.upload_image),
            contentDescription = ""
        )
    }
}