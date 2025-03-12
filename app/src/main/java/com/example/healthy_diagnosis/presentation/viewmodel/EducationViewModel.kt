package com.example.healthy_diagnosis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.EducationEntity
import com.example.healthy_diagnosis.domain.repositories.EducationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EducationViewModel @Inject constructor(
    private val educationRepository: EducationRepository
): ViewModel() {

    //du lieu danh sach
    private val _educationList = MutableStateFlow<List<EducationEntity>>(emptyList())
    val educationList = _educationList


    private val _addEducationResult = MutableStateFlow<String?>(null)
    val addEducationResult: StateFlow<String?> = _addEducationResult

    //Tranag thai loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    //lay danh sach education tu database
    fun fetchEducations(){
        viewModelScope.launch {
            _isLoading.value = true
            _educationList.value = educationRepository.getAllEducation()
            _isLoading.value = false

        }
    }

    //them education
    fun addEducation(name: String) {
        viewModelScope.launch {
            try {
                val educationEntity = EducationEntity(name = name)
                educationRepository.insertEducation(educationEntity)
                _addEducationResult.value = "Thêm thành công"
                fetchEducations()
            } catch (e: Exception) {
                _addEducationResult.value = "Lỗi: ${e.message}"
            }
        }
    }

    //xoa education
    fun deleteEducation(id: Int) {
        viewModelScope.launch {
            educationRepository.deleteEducation(id)
            fetchEducations()
        }
    }

    fun clearAddEducationResult() {
        _addEducationResult.value = null
    }
}