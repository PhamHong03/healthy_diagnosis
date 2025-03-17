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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
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

    private val _userRole = MutableStateFlow("Unknown")
    val userRole: StateFlow<String> = _userRole

    private val _isFirstLogin = MutableStateFlow<Boolean?>(null)  // Tránh null
    val isFirstLogin: StateFlow<Boolean?> = _isFirstLogin

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _authState = MutableStateFlow<Result<String>?>(null)
    val authState: StateFlow<Result<String>?> = _authState

    private val _loginState = MutableStateFlow<Result<String>?>(null)
    val loginState: StateFlow<Result<String>?> = _loginState


    private val _trigger = MutableStateFlow(0)

    fun checkFirstLogin() {
        FirebaseAuth.getInstance().currentUser?.uid?.let { uid ->
            FirebaseDatabase.getInstance().getReference("users/$uid").get()
                .addOnSuccessListener { snapshot ->
                    val firstLogin = snapshot.child("isFirstLogin").getValue(Boolean::class.java) ?: false
                    _isFirstLogin.value = firstLogin
                }
        }
    }



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
                            _userRole.value = accountRequest.role
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
                val uid = firebaseAuthRepository.getFirebaseUid()
                if (uid != null) {
                    fetchUserRole(uid)
                    _loginState.value = Result.success("Đăng nhập thành công!")
                } else {
                    _loginState.value = Result.failure(Exception("Không tìm thấy UID Firebase"))
                }
            } else {
                _loginState.value = Result.failure(result.exceptionOrNull() ?: Exception("Lỗi đăng nhập"))
            }
        }
    }

    fun fetchUserRole(uid: String) {
        if (uid.isEmpty()) {
            Log.e("AuthViewModel", "UID không hợp lệ!")
            return
        }

        val docRef = FirebaseFirestore.getInstance().collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val role = document.getString("role")
                    val firstLogin = document.getBoolean("isFirstLogin") ?: true

                    if (!role.isNullOrEmpty()) {
                        _userRole.value = role
                        _isFirstLogin.value = firstLogin
                        Log.d("AuthViewModel", "Vai trò: $role, Lần đầu đăng nhập: $firstLogin")
                    } else {
                        Log.e("AuthViewModel", "Trường 'role' không tồn tại hoặc rỗng")
                    }
                } else {
                    Log.e("AuthViewModel", "Không tìm thấy tài khoản với UID: $uid")
                }
            }
            .addOnFailureListener { e ->
                Log.e("AuthViewModel", "Lỗi lấy vai trò người dùng từ Firestore: ${e.message}")
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
