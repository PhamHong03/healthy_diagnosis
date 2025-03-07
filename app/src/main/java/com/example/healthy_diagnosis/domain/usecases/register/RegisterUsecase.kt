package com.example.healthy_diagnosis.domain.usecases.register

import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RegisterUsecase @Inject constructor(
private val authRepository : AccountRepository
){
    suspend fun execute(registerRequest: RegisterRequest): Response<ResponseBody> {
        return authRepository.registerAccount(registerRequest)
    }
}