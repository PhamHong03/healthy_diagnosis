package com.example.healthy_diagnosis.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "images",
    foreignKeys = [
        ForeignKey(
            entity = PhysicianEntity::class,
            parentColumns = ["id"],
            childColumns = ["physician_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DiseaseEntity::class,
            parentColumns = ["id"],
            childColumns = ["diseases_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AppointmentFormEntity::class,
            parentColumns = ["id"],
            childColumns = ["appointment_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class ImagesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val path_name : String,
    val images_created: String,
    val physician_id: Int,
    val diseases_id :Int? = 0 ,
    val appointment_id: Int
)