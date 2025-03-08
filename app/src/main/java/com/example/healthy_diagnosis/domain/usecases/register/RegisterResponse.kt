package com.example.healthy_diagnosis.domain.usecases.register

data class RegisterResponse(
    val id: Int,
    val username:String,
    val email:String,
    val phoneNumber:String,
    val role:String
)
