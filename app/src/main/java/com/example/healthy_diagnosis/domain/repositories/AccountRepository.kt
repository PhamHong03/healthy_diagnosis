package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.AccountEntity
import com.example.healthy_diagnosis.domain.usecases.login.LoginResponse
import com.example.healthy_diagnosis.domain.usecases.register.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response

interface AccountRepository {

    suspend fun insertAccount(account: AccountEntity)

    suspend fun getAccountById(id: String): AccountEntity?

    suspend fun registerAccount(account: RegisterRequest): Response<ResponseBody>

    suspend fun loginWithToken(token:String): Result<LoginResponse>

    suspend fun sendFirebaseToken(token: String): Result<Unit>
}