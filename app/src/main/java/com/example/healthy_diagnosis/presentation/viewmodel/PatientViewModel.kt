package com.example.healthy_diagnosis.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun deletePatient(patientId: Int) {
        viewModelScope.launch {
            patientRepository.deletePatient(patientId)
            fetchPatients()
        }
    }

    fun addPatient(patient: PatientRepository) {
        viewModelScope.launch {
            patientRepository.insertPatient(patient)
            fetchPatients()
        }
    }
}
