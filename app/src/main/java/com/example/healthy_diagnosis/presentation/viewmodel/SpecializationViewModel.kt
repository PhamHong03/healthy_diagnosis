package com.example.healthy_diagnosis.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.EducationEntity
import com.example.healthy_diagnosis.data.models.SpecializationEntity
import com.example.healthy_diagnosis.domain.repositories.SpecializationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import javax.inject.Inject

@HiltViewModel
class SpecializationViewModel @Inject constructor(
    private val specializationRepository: SpecializationRepository
):ViewModel() {

    private val _specializationList = MutableStateFlow<List<SpecializationEntity>>(emptyList())
    val specializationList = _specializationList.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun fetchSpecializations(){
        viewModelScope.launch {
            _isLoading.value = true
            _specializationList.value = specializationRepository.getSpecializations()
            _isLoading.value = false
        }
    }

    fun getAllSpecialization(){
        fetchSpecializations()
    }
}