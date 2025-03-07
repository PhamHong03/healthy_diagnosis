package com.example.healthy_diagnosis.domain.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepository @Inject constructor() {
    suspend fun registerFirebaseUser(email: String, password: String): Result<String> =
        suspendCoroutine { continuation ->
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Result.success("Đăng ký thành công!"))
                    } else {
                        continuation.resume(Result.failure(task.exception ?: Exception("Lỗi Firebase")))
                    }
                }
        }

    suspend fun loginFirebaseUser(email: String, password: String): Result<String> =
        suspendCoroutine { continuation ->
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Result.success("Đăng nhập thành công!"))
                    } else {
                        continuation.resume(Result.failure(task.exception ?: Exception("Lỗi Firebase")))
                    }
                }
        }

    suspend fun getFirebaseToken(): String? {
        return try {
            val user = Firebase.auth.currentUser ?: return null
            val tokenResult = user.getIdToken(true).await()
            tokenResult.token
        } catch (e: Exception) {
            null
        }
    }
}
