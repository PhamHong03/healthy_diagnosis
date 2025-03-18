package com.example.healthy_diagnosis.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity (tableName = "patients")
data class PatientEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val day_of_birth : String,
    val gender : String,
    val phone : String,
    val email: String,
    val job: String,
    val medical_code_card: String,
    val code_card_day_start: String,
    val status: Int
)