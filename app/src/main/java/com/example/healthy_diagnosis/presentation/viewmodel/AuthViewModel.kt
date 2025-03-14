package com.example.healthy_diagnosis.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.datasources.remote.ApiService
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.domain.repositories.FirebaseAuthRepository
import com.example.healthy_diagnosis.domain.usecases.TokenRequest
import com.example.healthy_diagnosis.domain.usecases.register.RegisterRequest
import com.example.healthy_diagnosis.domain.usecases.register.RegisterUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUsecase: RegisterUsecase,
    private val repository: AccountRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val apiService: ApiService
) : ViewModel() {


    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _authState = MutableStateFlow<Result<String>?>(null)
    val authState: StateFlow<Result<String>?> = _authState

    private val _loginState = MutableStateFlow<Result<String>?>(null)
    val loginState: StateFlow<Result<String>?> = _loginState


    private val _trigger = MutableStateFlow(0)
    fun registerAccount(accountRequest: RegisterRequest) {
        viewModelScope.launch {
            try {
                val result = firebaseAuthRepository.registerFirebaseUser(
                    accountRequest.username,
                    accountRequest.email,
                    accountRequest.phone_number,
                    accountRequest.password,
                    accountRequest.role
                )
                if (result.isSuccess) {
                    val firebaseUid = firebaseAuthRepository.getFirebaseUid()
                    Log.d("AuthViewModel", "Firebase UID: $firebaseUid")

                    firebaseUid?.let {
                        val response = registerUsecase.execute(accountRequest.copy(firebase_uid = firebaseUid))
                        if (response.isSuccessful) {
                            Log.d("AuthViewModel", "Server register success!")
                            _authState.value = Result.success("Đăng ký thành công!")
                        } else {
                            Log.e("AuthViewModel", "Server register failed: ${response.errorBody()?.string()}")
                            _authState.value = Result.failure(Exception("Đăng ký thất bại: ${response.errorBody()?.string()}"))
                        }
                    } ?: Log.e("AuthViewModel", "Firebase UID is null")
                } else {
                    val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                    Log.e("AuthViewModel", "Firebase register error: $errorMessage")
                    _authState.value = Result.failure(result.exceptionOrNull() ?: Exception("Lỗi Firebase"))
                }
            } catch (e: Exception) {
                _authState.value = Result.failure(e)
            }
            _trigger.value++
        }
    }


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val result = firebaseAuthRepository.loginFirebaseUser(email, password)
            if (result.isSuccess) {
                val token = firebaseAuthRepository.getFirebaseToken()
                token?.let { sendTokenToServer(it) }
                _loginState.value = Result.success("Đăng nhập thành công!")
            } else {
                _loginState.value = Result.failure(result.exceptionOrNull() ?: Exception("Lỗi đăng nhập"))
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = null
    }
    private fun sendTokenToServer(token: String) {
        viewModelScope.launch {
            try {
                val tokenRequest = TokenRequest(idToken = token)
                val response = apiService.sendFirebaseToken(tokenRequest)

                if (response.isSuccessful) {
                    Log.d("MyViewModel", "Token đã gửi thành công!")
                } else {
                    Log.e("MyViewModel", "Gửi token thất bại: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MyViewModel", "Exception khi gửi token: ${e.message}")
            }
        }
    }
}
