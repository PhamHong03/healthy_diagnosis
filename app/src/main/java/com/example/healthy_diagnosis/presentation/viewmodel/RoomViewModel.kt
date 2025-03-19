package com.example.healthy_diagnosis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.RoomEntity
import com.example.healthy_diagnosis.domain.repositories.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomRepository: RoomRepository
): ViewModel() {
    private val _roomList = MutableStateFlow<List<RoomEntity>>(emptyList())
    val roomList = _roomList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchRoom(){
        viewModelScope.launch {
            _isLoading.value = true
            _roomList.value = roomRepository.getAllRooms()
            _isLoading.value = false
        }
    }
}