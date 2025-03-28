package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.AccountEntity
import com.example.healthy_diagnosis.data.models.PhysicianEntity
import com.example.healthy_diagnosis.domain.usecases.TokenRequest
import com.example.healthy_diagnosis.domain.usecases.login.LoginResponse
import com.example.healthy_diagnosis.domain.usecases.register.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("accounts")
    suspend fun getAllAccount(): List<AccountEntity>

    @POST("get_account_id")
    suspend fun getAccountIdByUid(@Body request: Map<String, String>): Response<LoginResponse>

    @GET("physicians/account/{accountId}")
    suspend fun getPhysicianByAccountId(@Path("accountId") accountId: Int): Response<PhysicianEntity?>

}