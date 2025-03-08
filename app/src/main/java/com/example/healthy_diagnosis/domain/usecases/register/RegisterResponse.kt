package com.example.healthy_diagnosis.domain.usecases.register

data class RegisterResponse(
    val id: Int,
    val username:String,
    val email:String,
    val phonember:String,
    val role:String
)
