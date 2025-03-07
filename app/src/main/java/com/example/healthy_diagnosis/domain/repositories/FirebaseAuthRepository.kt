package com.example.healthy_diagnosis.domain.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.api.ApiService
import com.example.healthy_diagnosis.domain.usecases.login.LoginResponse
import com.example.healthy_diagnosis.domain.usecases.register.RegisterResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun registerFirebaseUser(username: String, email: String, phone_number: String, password: String, role: String): Result<String> =
        suspendCoroutine { continuation ->
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser
                        Log.d("FirebaseAuthRepository", "User created: ${user?.uid}")
                        user?.let {
                            val userProfile = mapOf(
                                "username" to username,
                                "email" to email,
                                "phone_number" to phone_number,
                                "role" to role
                            )
                            val db = Firebase.firestore
                            db.collection("users").document(user.uid)
                                .set(userProfile)
                                .addOnSuccessListener {
                                    Log.d("FirebaseAuthRepository", "User data saved to Firestore")
                                    continuation.resume(Result.success("Đăng ký thành công!"))
                                }
                                .addOnFailureListener { e ->
                                    Log.e("FirebaseAuthRepository", "Error saving user data: ${e.message}")
                                    continuation.resume(Result.failure(e))
                                }
                        }
                    } else {
                        val errorMessage = task.exception?.message ?: "Lỗi Firebase không xác định"
                        Log.e("FirebaseAuthRepository", "Error creating user: $errorMessage") // Thêm log
                        continuation.resume(Result.failure(task.exception ?: Exception("Lỗi Firebase")))
                    }
                }
        }
//
//    suspend fun loginFirebaseUser(email: String, password: String): Result<String> =
//        suspendCoroutine { continuation ->
//            Firebase.auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        continuation.resume(Result.success("Đăng nhập thành công!"))
//                    } else {
//                        continuation.resume(Result.failure(task.exception ?: Exception("Lỗi Firebase")))
//                    }
//                }
//        }
    suspend fun loginFirebaseUser(email: String, password: String): Result<LoginResponse> {
        return try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()

            val idToken = FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()?.token

            if (idToken.isNullOrEmpty()) {
                return Result.failure(Exception("Không thể lấy ID token"))
            }

            val response: Response<LoginResponse> = apiService.loginAccount("Bearer $idToken")

            if(response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            }else {
                Result.failure(Exception("Lỗi đăng nhập: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getFirebaseToken(): String? {
        return try {
            val user = Firebase.auth.currentUser
            if (user == null) {
                Log.e("FirebaseAuthRepository", "User not logged in")
                return null
            }
            val tokenResult = user.getIdToken(true).await()
            Log.d("FirebaseAuthRepository", "Token: ${tokenResult.token}")
            tokenResult.token
        } catch (e: Exception) {
            Log.e("FirebaseAuthRepository", "Error getting token: ${e.message}")
            null
        }
    }

    fun getFirebaseUid(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

}
