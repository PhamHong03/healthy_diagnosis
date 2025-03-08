package com.example.healthy_diagnosis.infrastructure.repositories


import android.util.Log
import com.example.healthy_diagnosis.data.api.ApiService
import com.example.healthy_diagnosis.data.entities.AccountEntity
import com.example.healthy_diagnosis.domain.usecases.register.RegisterResponse
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.domain.repositories.FirebaseAuthRepository
import com.example.healthy_diagnosis.domain.usecases.register.RegisterRequest
import com.example.healthy_diagnosis.infrastructure.datasources.AccountDAO
import com.example.healthy_diagnosis.infrastructure.datasources.AppDatabase
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import okhttp3.ResponseBody
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.tasks.await
import retrofit2.Response

class AccountRepositoryImpl (
    private val database: AppDatabase,
    private val apiService: ApiService
): AccountRepository {
    private val accountDao = database.accountDao()

    override suspend fun insertAccount(account: AccountEntity) {
        accountDao.insertAccount(account)
    }

    override suspend fun getAccountById(id: String): AccountEntity? {
        return accountDao.getAccount(id)
    }

    override suspend fun registerAccount(registerRequest: RegisterRequest): Response<ResponseBody> {
//        val token = getFirebaseToken().await()
        val token = FirebaseAuthRepository().getFirebaseToken() ?: ""
        if (token.isEmpty()) {
            return Response.error(401, ResponseBody.create(null, "Token không hợp lệ"))
        }
        return apiService.registerAccount("Bearer $token", registerRequest)
    }

    override suspend fun loginWithToken(token: String): RegisterResponse {
        val response = apiService.loginAccount("Bearer $token")
        if(response.isSuccessful) {
            return response.body()!!
        }else{
            throw Exception("Login failed")
        }
    }
    override suspend fun sendFirebaseToken(token: String): Result<Unit> {
        return try {
            val response = apiService.sendFirebaseToken(token)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Gửi token thất bại: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}