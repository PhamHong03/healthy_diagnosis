package com.example.healthy_diagnosis.infrastructure.repositories


import com.example.healthy_diagnosis.data.api.ApiService
import com.example.healthy_diagnosis.data.entities.AccountEntity
import com.example.healthy_diagnosis.domain.usecases.register.RegisterResponse
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.domain.usecases.register.RegisterRequest
import com.example.healthy_diagnosis.infrastructure.datasources.AccountDAO
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import okhttp3.ResponseBody
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.tasks.await
import retrofit2.Response

class AccountRepositoryImpl (
    private val accountDao : AccountDAO,
    private val apiService: ApiService
): AccountRepository {
    override suspend fun insertAccount(account: AccountEntity) {
        accountDao.insertAccount(account)
    }

    override suspend fun getAccountById(id: String): AccountEntity? {
        return accountDao.getAccount(id)
    }

    override suspend fun registerAccount(registerRequest: RegisterRequest): Response<ResponseBody> {
        val token = getFirebaseToken().await()
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


    private fun getFirebaseToken(): Task<String> {
        val  account = FirebaseAuth.getInstance().currentUser
        return account?.getIdToken(true)?.continueWith{task -> task.result?.token ?: ""}
            ?: Tasks.forResult("")
    }
}