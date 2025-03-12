package com.example.healthy_diagnosis.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "educations")
data class EducationEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val name: String
)