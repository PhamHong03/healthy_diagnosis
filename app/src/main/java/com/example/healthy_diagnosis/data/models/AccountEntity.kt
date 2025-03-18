package com.example.healthy_diagnosis.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val email : String,
    val username : String,
    @SerializedName("phone_number") val phoneNumber : String,
    val role : String,
    val password: String
)

