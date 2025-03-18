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


    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> get() = _isSaved

    private val _isLoading = MutableStateFlow(false)

    val isLoading = _isLoading.asStateFlow()


    // lay list physican from database
    fun fetchPhysician(){
        viewModelScope.launch {
            _isLoading.value = true
            _physicianList.value = physicianRepository.getAllPhysician()
            _isLoading.value = false

        }
    }
    fun insertPhysician(name: String, email: String, phone: String, address: String, gender: String, education_id: Int, specialization_id: Int){
        viewModelScope.launch {
            try {
                Log.d("InsertPhysician", "Đang thêm bác sĩ: name=$name, specializationId=$specialization_id, educationId=$education_id")

                val physicianEntity = PhysicianEntity(name = name, email = email, phone = phone, address = address, gender = gender, education_id = education_id, specialization_id = specialization_id)
                physicianRepository.insertPhysician(physicianEntity)

                Log.d("InsertPhysician", "Nhập thông tin thành công")
                fetchPhysician()
            } catch (e: Exception) {
                Log.d("InsertPhysician", "Checking specializationId: $specialization_id")

                Log.e("InsertPhysician", "Lỗi khi thêm bác sĩ", e)

            }
        }
    }

//    fun insertPhysician(name: String, email: String, phone: String, address: String, gender: String, specializationId: Int, educationId: Int){
//        viewModelScope.launch {
//            try {
//                val physicianEntity = PhysicianEntity(name = name, email = email, phone = phone, address = address, gender = gender, specializationId = specializationId, educationId = educationId)
//                physicianRepository.insertPhysician(physicianEntity)
//                _addphysicianResult.value = " Thêm thành công"
//
//                Log.d("Physicians", "Nhập thông tin thành công")
//                fetchPhysician()
//            }catch (e:Exception){
//                _addphysicianResult.value = "Lỗi : ${e.message}"
//                Log.e("Physicians", "Lỗi : ${e.message}")
//            }
//        }
//    }

    fun insertPhysician1(name: String, email: String, phone: String, address: String, gender: String, specializationId: Int, educationId: Int){
        viewModelScope.launch {
            try {
                val physicianEntity = PhysicianEntity(name = name, email = email, phone = phone, address = address, gender = gender, education_id = educationId,  specialization_id = specializationId)
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
}