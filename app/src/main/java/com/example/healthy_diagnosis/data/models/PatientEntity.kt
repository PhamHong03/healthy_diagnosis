package com.example.healthy_diagnosis.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "patients")
data class PatientEntity (
    @PrimaryKey(autoGenerate = true) val id: Int ,
    val name: String,
    @ColumnInfo(name = "day_of_birth") val dayOfBirth : String,
    val gender : String,
    val phone : String,
    val email: String,
    val job: String,
    @ColumnInfo(name = "medical_code_card") val medicalCodeCard: String,
    @ColumnInfo(name = "code_card_day_start") val codeCardDayStart: String,
    val status: Int
)