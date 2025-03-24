package com.example.healthy_diagnosis.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "appointment_forms",
    foreignKeys = [
        ForeignKey(
            entity = ApplicationFormEntity::class,
            parentColumns = ["id"],
            childColumns = ["application_form_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AppointmentFormEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val description: String,
    val application_form_id: Int
)
