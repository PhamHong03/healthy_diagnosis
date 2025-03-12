package com.example.healthy_diagnosis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.domain.repositories.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class PatientViewModel(
    private val patientRepository: PatientRepository
): ViewModel() {

    private val _patientList = MutableStateFlow<List<PatientEntity>>(emptyList())
    private val patientList = _patientList

    private  val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun fetchPatient() {
        viewModelScope.launch {
            _isLoading.value = true
            _patientList.value = patientRepository.getAllPatient()
            _isLoading.value = false
        }
    }


}