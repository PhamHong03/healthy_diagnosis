package com.example.healthy_diagnosis.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_disease")
data class CategoryDiseaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val category_disease_name : String,
    val category_disease_description : String
)

