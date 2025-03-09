package com.example.healthy_diagnosis.core.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthy_diagnosis.R

@Composable
fun MenuItemDrawer(
    text: String,
    iconResId: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            tint = Color(0xFF008ECC),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
@Composable
fun DrawerMenu(
    onMenuItemClick: (String) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ){
        ModalDrawerSheet {
            Row (
                modifier = Modifier.clickable { onMenuItemClick("Home") },
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(
                        id = R.string.app_logo
                    )
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    stringResource(id = R.string.the_logo_text),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Divider()
            MenuItemDrawer(text = "Danh sách", iconResId = R.drawable.note_stroke_rounded) { onMenuItemClick("list") }
            MenuItemDrawer(text = "Chẩn đoán", iconResId = R.drawable.cameradiag) { onMenuItemClick("Diagnosis") }
            MenuItemDrawer(text = "CSSK", iconResId = R.drawable.cssk) { onMenuItemClick("CSSK") }
            MenuItemDrawer(text = "Kết quả", iconResId = R.drawable.result) { onMenuItemClick("Result") }
            MenuItemDrawer(text = "Ghi chú", iconResId = R.drawable.note) { onMenuItemClick("Notes") }
            MenuItemDrawer(text = "Hội chuẩn", iconResId = R.drawable.video) { onMenuItemClick("HoiChuan") }
            MenuItemDrawer(text = "Cá nhân", iconResId = R.drawable.user) { onMenuItemClick("Personal") }
            MenuItemDrawer(text = "Cài đặt", iconResId = R.drawable.setting) { onMenuItemClick("Setting") }
            MenuItemDrawer(text = "Logout", iconResId = R.drawable.user) { onMenuItemClick("Logout") }
        }
    }
}