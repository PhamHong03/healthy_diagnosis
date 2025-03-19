package com.example.healthy_diagnosis.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "room")
data class RoomEntity (
    @PrimaryKey val id: String = "",
    val name: String
)