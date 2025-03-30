package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.AppointmentFormDao
import com.example.healthy_diagnosis.data.datasources.remote.AppointmentFormApiService
import com.example.healthy_diagnosis.data.models.AppointmentFormEntity
import com.example.healthy_diagnosis.domain.repositories.AppointmentFormRepository
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormRequest
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormResponse
import com.example.healthy_diagnosis.domain.usecases.appointment.toEntity
import javax.inject.Inject

class AppointmentFormRepositoryImpl @Inject constructor(
    private val appointmentFormDao: AppointmentFormDao,
    private val appointmentFormApiService: AppointmentFormApiService
): AppointmentFormRepository {
    override suspend fun getAllAppointmentForms(): List<AppointmentFormEntity> {
        return kotlin.runCatching {
            val apiForms = appointmentFormApiService.getAllAppointmentForms()
            val entities = apiForms.map { it.toEntity() }
            appointmentFormDao.insertAllAppointmentForm(entities)
            entities
        }.getOrElse {
            Log.e("AppointmentFormRepo", "Lỗi kết nối API: ${it.message}")
            appointmentFormDao.getAllAppointmentForms()
        }
    }
    override suspend fun insertAppointmentForm(appointmentFormEntity: AppointmentFormEntity) : Int{
        kotlin.runCatching {
            val request = AppointmentFormRequest(
                description = appointmentFormEntity.description,
                application_form_id = appointmentFormEntity.application_form_id
            )

            Log.d("AppointmentFormRepo", "Gửi request API: $request")

            val response = appointmentFormApiService.insertAppointmentForm(request)

            if (response.isSuccessful) {
                Log.d("AppointmentFormRepo", "Phản hồi API thành công: ${response.body()}")

                // Kiểm tra application_form_id có tồn tại trước khi insert
                val exists = appointmentFormDao.doesApplicationFormExist(appointmentFormEntity.application_form_id)
                if (!exists) {
                    Log.e("AppointmentFormRepo", "Lỗi: application_form_id ${appointmentFormEntity.application_form_id} không tồn tại")
                    return@runCatching
                }

                // Nếu tồn tại thì mới insert
                appointmentFormDao.insertAppointmentForm(appointmentFormEntity)
            } else {
                Log.e("AppointmentFormRepo", "API lỗi: ${response.code()} - ${response.errorBody()?.string()}")
            }
        }.onFailure {
            Log.e("AppointmentFormRepo", "Lỗi Exception: ${it.message}")
        }
    }

    override suspend fun getAppointmentFormById(id: Int): AppointmentFormEntity? {
        return kotlin.runCatching {
            val localAppointment = appointmentFormDao.getAppointmentFormById(id)
            if (localAppointment != null) {
                return@runCatching localAppointment
            }

            val apiResponse = appointmentFormApiService.getAppointmentFormById(id)
            val appointmentEntity = apiResponse.toEntity()

            appointmentFormDao.insertAppointmentForm(appointmentEntity)
            appointmentEntity
        }.getOrElse {
            Log.e("AppointmentFormRepo", "Lỗi khi lấy phiếu khám: ${it.message}")
            null
        }
    }
}

