package com.example.healthy_diagnosis.domain.usecases.physician

import androidx.room.ColumnInfo

data class PhysicianRequest (
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val gender: String,
    @ColumnInfo(name = "specialization_id") val specializationId: Int,
    @ColumnInfo(name = "education_id") val educationId: Int

)