package com.example.healthy_diagnosis.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.type.DateTime

@Entity (tableName = "patients")
data class PatientEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val name: String,
    val day_of_birth : DateTime,
    val gender : String,
    val phone : String,
    val email: String,
    val job: String,
    val medical_code_card: String,
    val code_card_day_start: DateTime,
    val status: String
)