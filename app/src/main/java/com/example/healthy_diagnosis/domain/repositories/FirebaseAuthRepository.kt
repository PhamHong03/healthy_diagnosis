package com.example.healthy_diagnosis.domain.repositories


import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun registerFirebaseUser(email: String, password: String): Result<String> =
        suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
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
            firebaseAuth.signInWithEmailAndPassword(email, password)
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
            val user = firebaseAuth.currentUser ?: return null
            val tokenResult = user.getIdToken(true).await()
            tokenResult.token
        } catch (e: Exception) {
            null
        }
    }
}
