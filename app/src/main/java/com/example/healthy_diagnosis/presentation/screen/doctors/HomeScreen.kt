package com.example.healthy_diagnosis.presentation.screen.doctors

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthy_diagnosis.R
import com.example.healthy_diagnosis.core.utils.ConfirmSaveDialog
import com.example.healthy_diagnosis.core.utils.HeaderSection
import com.example.healthy_diagnosis.core.utils.SearchBar
import com.example.healthy_diagnosis.data.models.MenuItemData
import com.example.healthy_diagnosis.presentation.viewmodel.AuthViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PhysicianViewModel
import com.example.healthy_diagnosis.ui.theme.BannerColor
import com.example.healthy_diagnosis.ui.theme.MenuItemColor

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    notificationCount: Int,
    physicianViewModel: PhysicianViewModel
){
    val items = listOf(
        MenuItemData("Công việc", R.drawable.cssk, "healthcare" ),
//        MenuItemData("Chẩn đoán", R.drawable.cameraa, "diagnosis"),
        MenuItemData("Danh sách", R.drawable.note_stroke_rounded, "patients" ),
        MenuItemData("Kết quả", R.drawable.result, "results"),
        MenuItemData("Ghi chú", R.drawable.note, "notes" ),
//        MenuItemData("Hội chuẩn", R.drawable.video, "meetings"),
        MenuItemData("Cá nhân", R.drawable.user, "profile"),
        MenuItemData("Cài đặt", R.drawable.setting, "settings" )
    )

    val accountId by authViewModel.account.collectAsState()
    var isDoctorRegistered by remember { mutableStateOf<Boolean?>(null) }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(accountId) {
        accountId?.id?.let { id ->
            physicianViewModel.fetchPhysicianByAccountId(id)
        }
    }

    val physicianId by physicianViewModel.physicianId.collectAsState()

    LaunchedEffect(physicianId) {
        showDialog = (physicianId == null)
    }

    LaunchedEffect(physicianViewModel.physicianId.collectAsState().value) {
        val currentPhysicianId = physicianViewModel.physicianId.value
        Log.d("HomeScreen", "Current physicianId from ViewModel: $currentPhysicianId")

        showDialog = (currentPhysicianId == null)
    }

    if(showDialog){
        ConfirmSaveDialog(
            onDismiss = {showDialog = false},
            onConfirm = {
                showDialog = false
                navController.navigate("input_infoDoctor")
            }
        )
    }
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
//            HeaderSection(authViewModel, notificationCount, "Hello Dr. ")
//            SearchBar()
//            Spacer(modifier = Modifier.height(10.dp))
            Banner(padding = 10.dp)
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp)
//                    .background(Color(0xFFBBDEFB), shape = RoundedCornerShape(12.dp)),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "🩺 Chăm sóc sức khỏe toàn diện\nDành cho bạn & gia đình",
//                    textAlign = TextAlign.Center,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF0D47A1)
//                )
//            }
//            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 30.dp)
                    .background(Color(0xFFBBDEFB), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🩺 Khám và chẩn đoán bệnh là trách nhiệm hàng đầu của bác sĩ!",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )
            }
            MenuGridWithRows(items, navController)

            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    Text(
                        text = "💙 Trung tâm y tế H&D - Sức khỏe là ưu tiên hàng đầu",
                        fontSize = 14.sp,
                        color = Color(0xFF1565C0),
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }

    }
}

@Composable
fun Banner(padding : Dp) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        colors = CardDefaults.cardColors(containerColor = BannerColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Trung tâm chuẩn đoán",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hãy luôn chăm sóc bản thân mỗi ngày, vì đó chính là cách bạn bảo vệ sức khỏe – tài sản quý giá nhất của mình.",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.doctor),
                    contentDescription = "Gift Box",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(start = 8.dp)
                )

            }
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color(0xFF83BDF7))

                ) {
                    Text(
                        text = "Xem thêm",
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItemData, navController: NavController, modifier: Modifier) {
    Box(
        modifier = Modifier
            .size(130.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF1F5F9))
            .padding(8.dp)
            .clickable {
                navController.navigate(item.destination)
                try {
                    item.destination?.let { destination ->
                        navController.navigate(destination)
                    } ?: Log.e("MenuItemCard", "Destination is null")
                } catch (e: Exception) {
                    Log.e("MenuItemCard", "Navigation error", e)
                }
            },

        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.title,
                tint = MenuItemColor,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun MenuGridWithRows(menuItems: List<MenuItemData>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        menuItems.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                rowItems.forEach { item ->
                    MenuItemCard(
                        item = item,
                        navController = navController,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
