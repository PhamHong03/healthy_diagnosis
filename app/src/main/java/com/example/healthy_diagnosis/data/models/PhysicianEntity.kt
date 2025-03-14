package com.example.healthy_diagnosis.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "physicians",
    foreignKeys = [
        ForeignKey(
            entity = SpecializationEntity::class,
            parentColumns = ["id"],
            childColumns = ["specialization_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EducationEntity::class,
            parentColumns = ["id"],
            childColumns = ["education_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PhysicianEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val gender: String,
    @ColumnInfo(name = "specialization_id") val specializationId: Int,
    @ColumnInfo(name = "education_id") val educationId: Int
)
