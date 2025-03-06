package com.example.healthy_diagnosis.data.api

import com.example.healthy_diagnosis.data.entities.AccountRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun registerAccount(
        @Header("Authorization") token: String,
        @Body account : AccountRequest
    ): Response<ResponseBody>
}