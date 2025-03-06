package com.example.healthy_diagnosis.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey val id: Int,
    val email : String,
    val username : String,
    val phone_number : String,
    val role : String,
    val password: String
)

data class AccountRequest(
    val email: String,
    val username: String,
    val password: String,
    val role: String,
    val phone_number: String
)
