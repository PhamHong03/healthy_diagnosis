package com.example.healthy_diagnosis.data.entities

data class MenuItemData(
    val title: String,
    val iconRes: Int,
    val destination: String,
    val badgeCount: String? = null
)
