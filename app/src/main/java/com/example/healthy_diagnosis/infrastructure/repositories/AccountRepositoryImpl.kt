package com.example.healthy_diagnosis.infrastructure.repositories


import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.AccountDAO
import com.example.healthy_diagnosis.data.datasources.remote.ApiService
import com.example.healthy_diagnosis.data.models.AccountEntity
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.domain.repositories.FirebaseAuthRepository
import com.example.healthy_diagnosis.domain.usecases.TokenRequest
import com.example.healthy_diagnosis.domain.usecases.login.LoginResponse
import com.example.healthy_diagnosis.domain.usecases.register.RegisterRequest
import com.example.healthy_diagnosis.data.datasources.local.AppDatabase
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
//    private val database: AppDatabase,
    private val accountDao: AccountDAO,
    private val apiService: ApiService,
    private val firebaseAuthRepository: FirebaseAuthRepository
): AccountRepository {
//    private val accountDao = database.accountDao()

    override suspend fun insertAccount(account: AccountEntity) {
        accountDao.insertAccount(account)
    }

    override suspend fun getAccountById(accountId: Int): AccountEntity? {
        return accountDao.getAccountById(accountId)

    }


    override suspend fun registerAccount(registerRequest: RegisterRequest): Response<ResponseBody> {
        val token = FirebaseAuthRepository(apiService).getFirebaseToken() ?: ""
        if (token.isEmpty()) {
            return Response.error(401, ResponseBody.create(null, "Token không hợp lệ"))
        }
        return apiService.registerAccount("Bearer $token", registerRequest)
    }
    override suspend fun loginWithToken(token: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(TokenRequest(token))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Lỗi không xác định"
                Result.failure(Exception("Lỗi đăng nhập: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun sendFirebaseToken(token: String): Result<Unit> {
        return try {
            val tokenRequest = TokenRequest(idToken = token)
            val response = apiService.sendFirebaseToken(tokenRequest)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Gửi token thất bại: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllAccount(): List<AccountEntity> {
        return runCatching {
            val accounts = apiService.getAllAccount()
            accountDao.insertAll(accounts)
            accounts
        }.getOrElse {
            Log.e("AccountRepo", "Lỗi kết nối API: ${it.message}")
            accountDao.getAllAccount()
        }
    }
}