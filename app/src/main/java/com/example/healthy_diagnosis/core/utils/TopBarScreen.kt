package com.example.healthy_diagnosis.core.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.example.healthy_diagnosis.ui.theme.BannerColor
import com.example.healthy_diagnosis.ui.theme.LightBlueWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarScreen(
    title: String,
    onBackClick: () -> Unit,
    actionIcon: ImageVector,
    onActionClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(imageVector = actionIcon, contentDescription = "Action")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = BannerColor,
            titleContentColor = Color.White
        )
    )
}

