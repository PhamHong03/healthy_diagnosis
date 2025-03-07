package com.example.healthy_diagnosis.domain.usecases.register

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String,
    val role: String,
    val phone_number: String
)
