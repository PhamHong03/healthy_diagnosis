package com.example.healthy_diagnosis.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.AppointmentFormEntity
import com.example.healthy_diagnosis.domain.repositories.AppointmentFormRepository
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormRequest
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormResponse
import com.example.healthy_diagnosis.domain.usecases.appointment.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentFormViewModel  @Inject constructor(
    private val appointmentFormRepository: AppointmentFormRepository
): ViewModel(){
//    private val _appointmentFormList = MutableStateFlow<List<AppointmentFormEntity>>(emptyList())
//    val appointmentFormList: StateFlow<List<AppointmentFormEntity>> = _appointmentFormList


    private val _appointmentFormList = MutableStateFlow<List<AppointmentFormEntity>>(emptyList())
    val appointmentFormList = _appointmentFormList

    private val _isSave = MutableStateFlow(false)
    val isSave: StateFlow<Boolean> get() = _isSave

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow: SharedFlow<String> = _eventFlow

    private val _appointmentId = MutableStateFlow<Int?>(null)
    val appointmentId: StateFlow<Int?> = _appointmentId

    init {
        fetchAppointmentForm()
    }

    fun fetchAppointmentForm(){
        viewModelScope.launch {
            _isLoading.value = true
            _appointmentFormList.value = appointmentFormRepository.getAllAppointmentForms()
            _isLoading.value = false
        }
    }

    fun insertAppointmentForm(appointmentFormRequest: AppointmentFormRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val appointmentEntity = appointmentFormRequest.toEntity()
                val response = appointmentFormRepository.insertAppointmentForm(appointmentEntity)

                if (response != null) {
                    _appointmentId.value = response.id

                    _appointmentFormList.value = appointmentFormRepository.getAllAppointmentForms()

                    _isSave.value = true
                    _eventFlow.emit("Thêm phiếu khám thành công!")
                } else {
                    _isSave.value = false
                    _eventFlow.emit("Không thể thêm phiếu khám!")
                }
            } catch (e: Exception) {
                _isSave.value = false
                _eventFlow.emit("Thêm phiếu khám thất bại!")
            } finally {
                _isLoading.value = false
            }
        }
    }
}