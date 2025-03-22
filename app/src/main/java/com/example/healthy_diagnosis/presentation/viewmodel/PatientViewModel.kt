package com.example.healthy_diagnosis.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.AccountEntity
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.domain.repositories.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PatientViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {

    private val _patientList = MutableStateFlow<List<PatientEntity>>(emptyList())
    val patientList: StateFlow<List<PatientEntity>> = _patientList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _addPatientResult = MutableStateFlow<String?>(null)
    val addPatientResult = _addPatientResult


    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> get() = _isSaved


    private val _patientByAccount = MutableStateFlow<PatientEntity?>(null)
    val patientByAccount: StateFlow<PatientEntity?> = _patientByAccount.asStateFlow()

    private val _patientId = MutableStateFlow<Int?>(null)
    val patientId: StateFlow<Int?> = _patientId.asStateFlow()

    fun fetchPatientIdByAccountId(accountId: Int) {
        viewModelScope.launch {
            val patient = patientRepository.getPatientByAccountId(accountId)
            _patientId.value = patient?.id
        }
    }
    fun getPatientByAccountId(accountId: Int) {
        viewModelScope.launch {
            try {
                val patient = patientRepository.getPatientByAccountId(accountId)
                _patientByAccount.value = patient
            } catch (e: Exception) {
                Log.e("PatientViewModel", "Lỗi khi lấy bệnh nhân theo account_id: ${e.message}")
            }
        }
    }
    init {
        fetchPatients()
    }
    fun fetchPatients() {
        viewModelScope.launch {
            _isLoading.value = true
            _patientList.value = patientRepository.getAllPatient()
            _isLoading.value = false
        }
    }

    fun insertPatient(
        name: String,
        day_of_birth: String,
        gender: String,
        phone: String,
        email: String,
        job: String,
        medical_code_card: String,
        code_card_day_start: String,
        status: Int,
        account_id: Int,
    ) {
        viewModelScope.launch {
            try {
                val patientEntity = PatientEntity(
                    account_id = account_id,
                    name = name,
                    day_of_birth = day_of_birth,
                    gender = gender,
                    phone = phone,
                    email = email,
                    job = job,
                    medical_code_card = medical_code_card,
                    code_card_day_start = code_card_day_start,
                    status = status
                )
                patientRepository.insertPatient(patientEntity)
                Log.d("InsertPatient", "Nhập thông tin thành công: $patientEntity")
                _isSaved.value  = true
                fetchPatients()
            }catch (e:Exception){
                Log.e("InsertPatient", "Lỗi khi thêm bệnh nhân", e)
                _isSaved.value = false
            }
        }
    }

    fun resetIsSave(){
        _isSaved.value = false

    }
    fun deletePatient(patientId: Int) {
        viewModelScope.launch {
            patientRepository.deletePatient(patientId)
            fetchPatients()
        }
    }

    fun addPatient(patientEntity: PatientEntity) {
        viewModelScope.launch {
            patientRepository.insertPatient(patientEntity)
            fetchPatients()
        }
    }
    fun getPatientIdByAccountId(accountId: Int, onResult: (Int?) -> Unit) {
        viewModelScope.launch {
            val patient = patientRepository.getPatientByAccountId(accountId)
            onResult(patient?.id)
        }
    }

//    fun getPatientNameById(patientId: Int, callback: (String?) -> Unit) {
//        viewModelScope.launch {
//            val patient = patientRepository.getPatientById(patientId)
//            callback(patient?.name)
//        }
//    }

}
