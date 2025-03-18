package com.example.healthy_diagnosis.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "specializations")
data class SpecializationEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val quantity_patient: Int
)