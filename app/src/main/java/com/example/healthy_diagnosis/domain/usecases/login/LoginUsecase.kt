package com.example.healthy_diagnosis.domain.usecases.login

import com.example.healthy_diagnosis.domain.usecases.register.RegisterResponse
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val authRepository : AccountRepository
){
    suspend operator fun invoke(token:String) : Result<LoginResponse> {
        return authRepository.loginWithToken(token)
    }
}