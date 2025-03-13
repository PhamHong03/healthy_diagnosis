package com.example.healthy_diagnosis.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "disease")
data class DiseaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    @ColumnInfo(name = "diagnose_disease_name") val diagnoseDiseaseName: String,
    @ColumnInfo(name = "diagnose_disease_description") val diagnoseDiseaseDescription: String,
    @ColumnInfo(name = "category_disease_id") val categoryDiseaseId: Int
)
