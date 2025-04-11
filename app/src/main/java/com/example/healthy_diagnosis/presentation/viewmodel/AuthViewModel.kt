package com.example.healthy_diagnosis.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.datasources.remote.ApiService
import com.example.healthy_diagnosis.data.models.AccountEntity
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.domain.repositories.FirebaseAuthRepository
import com.example.healthy_diagnosis.domain.usecases.TokenRequest
import com.example.healthy_diagnosis.domain.usecases.register.RegisterRequest
import com.example.healthy_diagnosis.domain.usecases.register.RegisterUsecase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUsecase: RegisterUsecase,
    private val repository: AccountRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val apiService: ApiService
) : ViewModel() {

    private val _account = MutableStateFlow<AccountEntity?>(null)
    val account: StateFlow<AccountEntity?> = _account

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _authState = MutableStateFlow<Result<String>?>(null)
    val authState: StateFlow<Result<String>?> = _authState

    private val _loginState = MutableStateFlow<Result<String>?>(null)
    val loginState: StateFlow<Result<String>?> = _loginState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _accountList = MutableStateFlow<List<AccountEntity>>(emptyList())
    val accountList = _accountList

    private val _isDoctorRegistered = mutableStateOf<Boolean?>(null)
    val isDoctorRegistered: State<Boolean?> = _isDoctorRegistered

    fun checkDoctorRegistration(accountId: Int) {
        viewModelScope.launch {
            val result = repository.getPhysicianByAccountId(accountId)
            _isDoctorRegistered.value = result
        }
    }


    fun setAccount(account: AccountEntity) {
        _account.value = account
    }

    fun getAccountId(): Int? {
        return _account.value?.id
    }

    init {
        fetchAccount()
    }

    fun fetchAccount() {
        viewModelScope.launch {
            _isLoading.value = true
            _accountList.value = repository.getAllAccount()
            _isLoading.value = false
        }
    }

    fun registerAccount(accountRequest: RegisterRequest) {
        viewModelScope.launch {
            try {
                val result = firebaseAuthRepository.registerFirebaseUser(
                    accountRequest.email,
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
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val result = firebaseAuthRepository.loginFirebaseUser(email, password)
            if (result.isSuccess) {
                val response = result.getOrNull()
                if (response != null) {
                    // ✅ Lưu account_id vào Room
                    val account = AccountEntity(
                        id = response.id,
                        email = response.email,
                        role = response.role,
                        password = ""  // 🔹 Không cần lưu mật khẩu
                    )
                    Log.d("AuthViewModel", "🔹 Account ID: ${account.id}")
                    repository.insertAccount(account)

                    _userRole.value = response.role
                    setAccount(account)
                    Log.d("AuthViewModel", "✅ Đăng nhập thành công: $account")

                    _loginState.value = Result.success("Đăng nhập thành công!")
                } else {
                    _loginState.value = Result.failure(Exception("Lỗi phản hồi từ server"))
                }
            } else {
                _loginState.value = Result.failure(result.exceptionOrNull() ?: Exception("Lỗi đăng nhập"))
            }
        }
    }

//    fun loginUser(email: String, password: String) {
//        viewModelScope.launch {
//            val result = firebaseAuthRepository.loginFirebaseUser(email, password)
//            if (result.isSuccess) {
//                val token = firebaseAuthRepository.getFirebaseToken()
//                token?.let { sendTokenToServer(it) }
//                val uid = firebaseAuthRepository.getFirebaseUid()
//                if (uid != null) {
//                    fetchUserRole(uid)
//                    _loginState.value = Result.success("Đăng nhập thành công!")
//                } else {
//                    _loginState.value = Result.failure(Exception("Không tìm thấy UID Firebase"))
//                }
//            } else {
//                _loginState.value = Result.failure(result.exceptionOrNull() ?: Exception("Lỗi đăng nhập"))
//            }
//        }
//    }

//    fun loginUser(email: String, password: String, context: Context) {
//        viewModelScope.launch {
//            val result = firebaseAuthRepository.loginFirebaseUser(email, password)
//
//            if (result.isSuccess) {
//                val token = firebaseAuthRepository.getFirebaseToken()
//                token?.let { sendTokenToServer(it) }
//
//                val uid = firebaseAuthRepository.getFirebaseUid()
//                if (uid != null) {
//                    fetchUserRole(uid)
//                    fetchAccountInfoAndSaveToRoom(uid)
//
//                    // 🔥 Gọi API lấy account_id
//                    try {
//                        val response = apiService.getAccountIdByUid(mapOf("firebase_uid" to uid))
////                        if (response.isSuccessful) {
////                            val loginResponse = response.body()
////                            val accountId = loginResponse?.id
////
////                            if (accountId != null) {
////                                saveAccountId(context, accountId)
////                                Log.d("AuthViewModel", "Account ID lấy được từ server: $accountId")
////                                _loginState.value = Result.success("Đăng nhập thành công!")
////                            } else {
////                                Log.e("AuthViewModel", "Không lấy được account_id từ server!")
////                                _loginState.value = Result.failure(Exception("Lỗi lấy account_id"))
////                            }
////                        } else {
////                            Log.e("AuthViewModel", "API trả về lỗi: ${response.errorBody()?.string()}")
////                            _loginState.value = Result.failure(Exception("Lỗi từ server khi lấy account_id"))
////                        }
//                        if (response.isSuccessful) {
//                            val responseBody = response.body()
//                            Log.d("AuthViewModel", "Response body: $responseBody")
//                        } else {
//                            Log.e("AuthViewModel", "Lỗi response: ${response.errorBody()?.string()}")
//                        }
//
//                    } catch (e: Exception) {
//                        Log.e("AuthViewModel", "Lỗi khi gọi API lấy account_id: ${e.message}")
//                        _loginState.value = Result.failure(e)
//                    }
//                } else {
//                    Log.e("AuthViewModel", "Không tìm thấy UID Firebase")
//                    _loginState.value = Result.failure(Exception("Không tìm thấy UID Firebase"))
//                }
//            } else {
//                Log.e("AuthViewModel", "Đăng nhập Firebase thất bại")
//                _loginState.value = Result.failure(result.exceptionOrNull() ?: Exception("Lỗi đăng nhập"))
//            }
//        }
//    }


    fun fetchUserRole(uid: String) {
        FirebaseFirestore.getInstance().collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userRole = document.getString("role")
                    _userRole.value = userRole
                }
            }
            .addOnFailureListener { e ->
                Log.e("AuthViewModel", "⚠️ Lỗi khi lấy role: ${e.message}")
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

    fun refreshFirebaseToken() {
        FirebaseAuth.getInstance().currentUser?.getIdToken(true)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newToken = task.result?.token
                    Log.d("FirebaseToken", "New Token: $newToken")
                } else {
                    Log.e("FirebaseToken", "Error: ${task.exception}")
                }
            }
    }


    private fun fetchAccountInfoAndSaveToRoom(uid: String) {
        val docRef = FirebaseFirestore.getInstance().collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val accountId = document.getLong("account_id")?.toInt() ?: return@addOnSuccessListener
                    val account = AccountEntity(
                        id = accountId,  // Lấy ID từ server để tránh trùng lặp
                        email = document.getString("email") ?: "",
                        role = document.getString("role") ?: "",
                        password = ""
                    )

                    viewModelScope.launch {
                        val existingAccount = repository.getAccountById(accountId)
                        if (existingAccount == null) {
                            repository.insertAccount(account)
                            Log.d("AuthViewModel", "Lưu tài khoản vào Room thành công!")
                        } else {
                            Log.d("AuthViewModel", "Tài khoản đã tồn tại, không cần lưu lại!")
                        }
                    }
                } else {
                    Log.e("AuthViewModel", "Không tìm thấy tài khoản với UID: $uid")
                }
            }
            .addOnFailureListener { e ->
                Log.e("AuthViewModel", "Lỗi lấy thông tin tài khoản từ Firestore: ${e.message}")
            }
    }


    fun saveAccountId(context: Context, accountId: Int) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("account_id", accountId).apply()
    }

    fun getAccountId(context: Context): Int? {
        val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPref.getInt("account_id", -1).takeIf { it != -1 }
    }

}
