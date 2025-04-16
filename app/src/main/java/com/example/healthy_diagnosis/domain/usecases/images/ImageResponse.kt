package com.example.healthy_diagnosis.domain.usecases.images

data class ImageResponse(
    val id: Int,
    val images_path: String?,
    val images_created: String,
    val physician_id: Int,
    val diseases_id: Int?,
    val appointment_id: Int
)
