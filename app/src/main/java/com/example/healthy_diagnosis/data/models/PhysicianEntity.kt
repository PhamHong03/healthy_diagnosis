package com.example.healthy_diagnosis.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val gender: String,
    val education_id: Int,
    val specialization_id: Int
)
