package com.example.healthy_diagnosis.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImagesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name : String,
    val description: String
)