package com.example.healthy_diagnosis.domain.usecases.login

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val userId: String? = null,
    val role: String? = null
)
