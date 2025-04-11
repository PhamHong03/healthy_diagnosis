package com.example.healthy_diagnosis.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "medical_histories",
    foreignKeys = [
        ForeignKey(
            entity = PhysicianEntity::class,
            parentColumns = ["id"],
            childColumns = ["physician_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MedicalHistoryEntity (
    @PrimaryKey val id : Int = 0,
    val description: String,
    val physician_id: Int,
    val physician_name: String,
    val calendar_date: String
)