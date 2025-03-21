package com.example.healthy_diagnosis.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.datasources.local.MedicalHistoryDao
import com.example.healthy_diagnosis.data.datasources.remote.MedicalHistoryApiService
import com.example.healthy_diagnosis.data.models.MedicalHistoryEntity
import com.example.healthy_diagnosis.domain.repositories.MedicalHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalHistoryViewModel @Inject constructor(
    private val medicalHistoryRepository: MedicalHistoryRepository
): ViewModel(){

    private val _medicalHistoryList = MutableStateFlow<List<MedicalHistoryEntity>>(emptyList())
    val medicalHistoryList = _medicalHistoryList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchMedicalHistory(){
        viewModelScope.launch {
            _isLoading.value = true
            _medicalHistoryList.value = medicalHistoryRepository.getAllMedicalHistory()
            _isLoading.value = false
        }
    }

}