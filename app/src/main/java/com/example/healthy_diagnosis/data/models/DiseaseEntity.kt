package com.example.healthy_diagnosis.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "disease")
data class DiseaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val diagnose_disease_name: String,
    val diagnose_disease_description: String,
    val category_disease_id: Int
)
