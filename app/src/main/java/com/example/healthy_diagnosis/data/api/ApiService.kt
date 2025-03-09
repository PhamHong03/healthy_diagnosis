package com.example.healthy_diagnosis.data.api

import com.example.healthy_diagnosis.domain.usecases.TokenRequest
import com.example.healthy_diagnosis.domain.usecases.login.LoginResponse
import com.example.healthy_diagnosis.domain.usecases.register.RegisterRequest
import com.example.healthy_diagnosis.domain.usecases.register.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun registerAccount(
        @Header("Authorization") token: String,
        @Body account : RegisterRequest
    ): Response<ResponseBody>

    @POST("login")
    suspend fun login(@Body request: TokenRequest): Response<LoginResponse>


    @POST("sendFirebaseToken")
    suspend fun sendFirebaseToken(
        @Body tokenRequest: TokenRequest
    ): Response<Unit>

}