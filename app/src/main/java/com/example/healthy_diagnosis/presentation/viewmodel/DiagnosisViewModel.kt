package com.example.healthy_diagnosis.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.domain.repositories.DiagnosisRepository
import com.example.healthy_diagnosis.domain.usecases.result.DiagnosisFullInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiagnosisViewModel @Inject constructor(
    private val diagnosisRepository: DiagnosisRepository
) : ViewModel() {

    private val _diagnosisDetail = MutableStateFlow<DiagnosisFullInfo?>(null)
    val diagnosisDetail = _diagnosisDetail.asStateFlow()

    fun loadDiagnosisDetail(appointmentId: Int) {
        viewModelScope.launch {
            try {
                val result = diagnosisRepository.getDiagnosisFullInfo(appointmentId)
                Log.d("DiagnosisViewModel", "Kết quả nhận được: $result")
                _diagnosisDetail.value = result
            } catch (e: Exception) {
                Log.e("DiagnosisViewModel", "Lỗi: ${e.message}")
            }
        }
    }
}
