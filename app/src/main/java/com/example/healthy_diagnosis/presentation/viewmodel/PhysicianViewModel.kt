package com.example.healthy_diagnosis.presentation.viewmodel

import android.util.Log
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.EducationEntity
import com.example.healthy_diagnosis.data.models.PhysicianEntity
import com.example.healthy_diagnosis.data.models.SpecializationEntity
import com.example.healthy_diagnosis.domain.repositories.PhysicianRepository
import com.example.healthy_diagnosis.domain.usecases.patient.PatientWithApplicationDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhysicianViewModel @Inject constructor(
    private val physicianRepository: PhysicianRepository
) : ViewModel() {

    private val _physicianList = MutableStateFlow<List<PhysicianEntity>>(emptyList())
    val physicianList = _physicianList

    private val _addphysicianResult = MutableStateFlow<String?>(null)
    val addPhysicianResult : StateFlow<String?> = _addphysicianResult

    //Save state
    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> get() = _isSaved

    //Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _physicianAccount = MutableStateFlow<PhysicianEntity?>(null)
    val physicianAccount: StateFlow<PhysicianEntity?> = _physicianAccount.asStateFlow()

    private val _physicianId = MutableStateFlow<Int?>(null)
    val physicianId: StateFlow<Int?> = _physicianId.asStateFlow()

    private val _patients = MutableLiveData<List<PatientWithApplicationDate>>()
    val patients: LiveData<List<PatientWithApplicationDate>> = _patients

    fun fetchPatients(physicianId: Int) {
        viewModelScope.launch {
            try {
                _patients.value = physicianRepository.getPatientsByPhysician(physicianId)
            } catch (e: Exception) {
                Log.e("PhysicianViewModel", "Error fetching patients: ${e}")
            }
        }
    }



    fun fetchPhysicianByAccountId(accountId: Int) {
        viewModelScope.launch {
            Log.d("PhysicianViewModel", "Fetching physician for accountId: $accountId")

            val physician = physicianRepository.getPhysicianByAccountId(accountId)

            if (physician != null) {
                Log.d("PhysicianViewModel", "Physician found: ${physician.id} - ${physician.name}")
            } else {
                Log.d("PhysicianViewModel", "No physician found for accountId: $accountId")
            }

            _physicianId.value = physician?.id
            Log.d("PhysicianViewModel", "Updated physicianId: ${_physicianId.value}")
        }
    }


    init {
        fetchPhysician()
    }
    suspend fun isDoctorExists(accountId: Int): Boolean {
        return physicianRepository.checkPhysicianExists(accountId)
    }
    fun fetchPhysician(){
        viewModelScope.launch {
            _isLoading.value = true
            _physicianList.value = physicianRepository.getAllPhysician()
            _isLoading.value = false

        }
    }
    fun insertPhysician(name: String, email: String, phone: String, address: String, gender: String, education_id: Int, specialization_id: Int, account_id: Int){
        viewModelScope.launch {
            try {
                val physicianEntity = PhysicianEntity(
                    account_id = account_id,
                    name = name,
                    email = email,
                    phone = phone,
                    address = address,
                    gender = gender,
                    education_id = education_id,
                    specialization_id = specialization_id
                )
                physicianRepository.insertPhysician(physicianEntity)

                Log.d("InsertPhysician", "Nhập thông tin thành công")

                _isSaved.value = true  // Cập nhật trạng thái khi lưu thành công
                fetchPhysician()

            } catch (e: Exception) {
                Log.e("InsertPhysician", "Lỗi khi thêm bác sĩ", e)
                _isSaved.value = false
            }
        }
    }

    fun insertPhysician1(
        name: String,
        email: String,
        phone: String,
        address: String,
        gender: String,
        specializationId: Int,
        educationId: Int,
        accountId: Int,
    ){
        viewModelScope.launch {
            try {
                val physicianEntity = PhysicianEntity(
                    name = name,
                    email = email,
                    phone = phone,
                    address = address,
                    gender = gender,
                    education_id = educationId,
                    specialization_id = specializationId,
                    account_id = accountId
                )
                physicianRepository.insertPhysician(physicianEntity)
                _addphysicianResult.value = " Thêm thành công"
                fetchPhysician()
            }catch (e:Exception){
                _addphysicianResult.value = "Lỗi :; ${e.message}"
            }
        }
    }

    fun deletePhysician(id: Int){
        viewModelScope.launch {
            physicianRepository.deletePhysician(id)
            fetchPhysician()
        }
    }

    fun resetIsSaved() {
        _isSaved.value = false
    }


}