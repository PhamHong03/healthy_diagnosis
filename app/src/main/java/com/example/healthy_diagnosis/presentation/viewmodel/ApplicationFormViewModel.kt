package com.example.healthy_diagnosis.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.ApplicationFormEntity
import com.example.healthy_diagnosis.data.models.MedicalHistoryEntity
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.domain.repositories.ApplicationFormRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApplicationFormViewModel @Inject constructor(
    private val applicationFormRespository: ApplicationFormRespository
): ViewModel() {

    private val _applicationFormList = MutableStateFlow<List<ApplicationFormEntity>>(emptyList())
    val applicationFormList: StateFlow<List<ApplicationFormEntity>> = _applicationFormList

    private val _isSave = MutableStateFlow(false)
    val isSave: StateFlow<Boolean> get() = _isSave

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> get() = _isSaved

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _patientId = MutableLiveData<PatientEntity?>()
    val patientId: LiveData<PatientEntity?> = _patientId

    fun fetchPatientByApplicationFormId(applicationFormId: Int) {
        viewModelScope.launch {
            try {
                Log.d("ApplicationFormVM", "Fetching patient for applicationFormId: $applicationFormId")
                val result = applicationFormRespository.getPatientByApplicationFormId(applicationFormId)
                Log.d("ApplicationFormVM", "Result from repo: $result")
                _patientId.postValue(result)
            } catch (e: Exception) {
                Log.e("ApplicationFormVM", "Error fetching patient: ${e.message}")
            }
        }
    }


    init {
        fetchApplicationForm()
    }
    fun fetchApplicationForm() {
        viewModelScope.launch {
            _isLoading.value = true

            val localData = applicationFormRespository.getAllApplicationForm()
            if (localData.isNotEmpty()) {
                _applicationFormList.value = localData
            } else {
                Log.e("ApplicationFormViewModel", "Room rỗng, gọi API để tải dữ liệu...")
                applicationFormRespository.getAllApplicationForm()
            }

            _isLoading.value = false
        }
    }

    fun insertApplicationForm(content: String, application_form_date: String, patient_id: Int, room_id: String, medical_history_id: Int) {
        viewModelScope.launch {
            try {
                if (patient_id == 0) {
                    Log.e("InsertApplicationForm", "Lỗi: Thiếu thông tin đăng ký khám!")
                    return@launch
                }
                val applicationFormEntity = ApplicationFormEntity(
                    content = content,
                    application_form_date = application_form_date,
                    patient_id = patient_id,
                    room_id = room_id,
                    medical_history_id = medical_history_id
                )
                applicationFormRespository.insertApplicationForm(applicationFormEntity)
                Log.d("InsertApplicationForm", "Đăng ký thành công")

                _isSave.value = true

                // Cập nhật lại UI sau khi insert
                fetchApplicationForm()
            } catch (e: Exception) {
                Log.e("InsertApplicationForm", "Lỗi khi thêm phiếu đăng ký", e)
                _isSave.value = false
            }
        }
    }

    fun deleteApplicationForm(id: Int){
        viewModelScope.launch {
            applicationFormRespository.deleteApplicationForm(id)
            fetchApplicationForm()
        }
    }
}