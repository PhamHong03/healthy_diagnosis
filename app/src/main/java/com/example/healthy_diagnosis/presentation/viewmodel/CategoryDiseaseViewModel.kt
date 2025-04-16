package com.example.healthy_diagnosis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.CategoryDiseaseEntity
import com.example.healthy_diagnosis.data.models.RoomEntity
import com.example.healthy_diagnosis.domain.repositories.CategoryDiseaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDiseaseViewModel @Inject constructor(
    private val categoryDiseaseRepository: CategoryDiseaseRepository
): ViewModel(){
    private val _categoryDisease = MutableStateFlow<List<CategoryDiseaseEntity>>(emptyList())
    val categoryDisease = _categoryDisease

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchCategoryDisease()
    }
    fun fetchCategoryDisease(){
        viewModelScope.launch {
            _isLoading.value = true
            _categoryDisease.value = categoryDiseaseRepository.getAllCategoryDisease()
            _isLoading.value = false
        }
    }
}