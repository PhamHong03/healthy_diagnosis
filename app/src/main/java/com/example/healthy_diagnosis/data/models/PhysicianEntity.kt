package com.example.healthy_diagnosis.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "physicians")
data class PhysicianEntity (
    @PrimaryKey(autoGenerate = true) val id : Int = 1,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val gender: String,
    val specialization_id: Int,
    val education_id: Int,
)