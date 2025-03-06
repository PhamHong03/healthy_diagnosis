package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.entities.Account
import com.example.healthy_diagnosis.data.entities.AccountRequest
import okhttp3.ResponseBody
import retrofit2.Response

interface AccountRepository {

    suspend fun insertAccount(account: Account)

    suspend fun getAccountById(id: String): Account?

    suspend fun registerAccount(account: AccountRequest): Response<ResponseBody>
}