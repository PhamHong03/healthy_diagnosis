package com.example.healthy_diagnosis.infrastructure.repositories


import com.example.healthy_diagnosis.data.api.ApiService
import com.example.healthy_diagnosis.data.entities.Account
import com.example.healthy_diagnosis.data.entities.AccountRequest
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
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
    override suspend fun insertAccount(account: Account) {
        accountDao.insertAccount(account)
    }

    override suspend fun getAccountById(id: String): Account? {
        return accountDao.getAccount(id)
    }

    override suspend fun registerAccount(accountRequest: AccountRequest): Response<ResponseBody> {
        val token = getFirebaseToken().await()
        return apiService.registerAccount("Bearer $token", accountRequest)
    }

    private fun getFirebaseToken(): Task<String> {
        val  account = FirebaseAuth.getInstance().currentUser
        return account?.getIdToken(true)?.continueWith{task -> task.result?.token ?: ""}
            ?: Tasks.forResult("")
    }

}