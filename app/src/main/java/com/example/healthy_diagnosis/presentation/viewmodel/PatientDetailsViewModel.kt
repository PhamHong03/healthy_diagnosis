package com.example.healthy_diagnosis.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.domain.repositories.AppointmentListForPatientRepository
import com.example.healthy_diagnosis.domain.repositories.DiagnosisRepository
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentDetails
import com.example.healthy_diagnosis.domain.usecases.result.DiagnosisFullInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientDetailsViewModel @Inject constructor(
    private val appointmentListForPatientRepository: AppointmentListForPatientRepository
) : ViewModel() {

    private val _appointmentDetails = MutableStateFlow<List<AppointmentDetails>>(emptyList())
    val diagnosisDetail: StateFlow<List<AppointmentDetails>> = _appointmentDetails.asStateFlow()

    fun loadDiagnosisDetail(patientId: Int) {
        viewModelScope.launch {
            try {
                val result = appointmentListForPatientRepository.getAllAppointmentByPatientId(patientId)
                Log.d("PatientDetailsViewModel", "Kết quả: $result")
                _appointmentDetails.value = result
            } catch (e: Exception) {
                Log.e("PatientDetailsViewModel", "Lỗi khi lấy dữ liệu: ${e.message}")
            }
        }
    }
}