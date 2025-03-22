package com.example.healthy_diagnosis.domain.usecases.login

//data class LoginResponse(
//    val id: Int,
//    val success: Boolean,
//    val message: String,
//    val userId: String? = null,
//    val role: String? = null
//
//)



data class LoginResponse(
    val id: Int,
    val email: String,
//    val username: String,
//    val phone_number: String,
    val role: String
)

