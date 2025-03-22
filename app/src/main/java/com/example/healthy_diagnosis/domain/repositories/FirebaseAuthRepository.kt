package com.example.healthy_diagnosis.domain.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.remote.ApiService
import com.example.healthy_diagnosis.domain.usecases.TokenRequest
import com.example.healthy_diagnosis.domain.usecases.login.LoginResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun registerFirebaseUser( email: String, password: String, role: String): Result<String> =
        suspendCoroutine { continuation ->
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser
                        Log.d("FirebaseAuthRepository", "User created: ${user?.uid}")
                        user?.let {
                            val userProfile = mapOf(
                                "email" to email,
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

    suspend fun loginFirebaseUser(email: String, password: String): Result<LoginResponse> {
        return try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            val idToken = getFirebaseToken() ?: return Result.failure(Exception("Không thể lấy ID token"))

            val response = apiService.login(TokenRequest(idToken))
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
