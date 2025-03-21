package com.example.healthy_diagnosis.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val id: Int,
    val email : String,
    val username : String,
    val phone_number : String,
    val role : String,
    val password: String = ""
)

