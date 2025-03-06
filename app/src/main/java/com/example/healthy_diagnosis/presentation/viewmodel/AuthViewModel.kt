package com.example.healthy_diagnosis.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.entities.AccountRequest
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel(){

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, username: String, phoneNumber: String, password:String) {
        viewModelScope.launch {
            val accountRequest = AccountRequest (
                email = email,
                username = username,
                password = password,
                role = "account",
                phone_number = phoneNumber
            )

            val response = accountRepository.registerAccount(accountRequest)
            if(response.isSuccessful) {
                Log.d("Register", "Success: ${response.body()?.string()}")
            }else{
                Log.e("Register", "Error: ${response.errorBody()?.string()}")
            }
        }
    }
}