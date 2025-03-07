package com.example.healthy_diagnosis.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val email : String,
    val username : String,
    val phone_number : String,
    val role : String,
    val password: String
)

