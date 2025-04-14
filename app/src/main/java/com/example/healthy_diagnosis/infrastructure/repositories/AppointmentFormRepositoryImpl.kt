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

//    override suspend fun insertAppointmentForm(appointmentFormEntity: AppointmentFormEntity): AppointmentFormResponse? {
//        return runCatching {
//            val request = AppointmentFormRequest(
//                description = appointmentFormEntity.description,
//                application_form_id = appointmentFormEntity.application_form_id
//            )
//            Log.d("InsertAppointmentForm", "application_form_id: ${appointmentFormEntity.application_form_id}")
//            val response = appointmentFormApiService.insertAppointmentForm(request)
//
//            if (response.isSuccessful) {
//                val body = response.body()
//
//                val exists = appointmentFormDao.doesApplicationFormExist(appointmentFormEntity.application_form_id)
//                if (exists && body != null) {
//                    appointmentFormDao.insertAppointmentForm(appointmentFormEntity)
//                    return@runCatching body
//                } else {
//                    Log.e("Repo", "Không tồn tại hoặc body null")
//                    null
//                }
//            } else {
//                Log.e("Repo", "Lỗi API: ${response.code()}")
//                null
//            }
//        }.getOrElse {
//            Log.e("Repo", "Lỗi Exception: ${it.message}")
//            null
//        }
//    }

    override suspend fun insertAppointmentForm(appointmentFormEntity: AppointmentFormEntity): AppointmentFormResponse? {
        return runCatching {
            val request = AppointmentFormRequest(
                description = appointmentFormEntity.description,
                application_form_id = appointmentFormEntity.application_form_id
            )

            val response = appointmentFormApiService.insertAppointmentForm(request)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                appointmentFormDao.insertAppointmentForm(body.toEntity())
                body
            } else {
                Log.e("Repo", "Insert thất bại - response: ${response.code()}, body: $body")
                null
            }
        }.getOrElse {
            Log.e("Repo", "Lỗi Exception: ${it.message}")
            null
        }
    }



//    override suspend fun getAppointmentFormById(id: Int): AppointmentFormEntity? {
//        return kotlin.runCatching {
//            val localAppointment = appointmentFormDao.getAppointmentFormById(id)
//            if (localAppointment != null) {
//                return@runCatching localAppointment
//            }
//
//            val apiResponse = appointmentFormApiService.getAppointmentFormById(id)
//            val appointmentEntity = apiResponse.toEntity()
//
//            appointmentFormDao.insertAppointmentForm(appointmentEntity)
//            appointmentEntity
//        }.getOrElse {
//            Log.e("AppointmentFormRepo", "Lỗi khi lấy phiếu khám: ${it.message}")
//            null
//        }
//    }

    override suspend fun getAppointmentFormById(id: Int): AppointmentFormEntity? {
        return kotlin.runCatching {
            val localAppointment = appointmentFormDao.getAppointmentFormById(id)
            if (localAppointment != null) {
                return@runCatching localAppointment
            }

            val apiResponse = appointmentFormApiService.getAppointmentFormById(id)

            if (apiResponse.isSuccessful) {
                val appointmentEntity = apiResponse.body()?.toEntity()

                if (appointmentEntity != null) {
                    appointmentFormDao.insertAppointmentForm(appointmentEntity)
                    return@runCatching appointmentEntity
                } else {
                    Log.e("AppointmentFormRepo", "Dữ liệu trả về từ API là null")
                    null
                }
            } else {
                Log.e("AppointmentFormRepo", "Lỗi API: ${apiResponse.code()}")
                null
            }
        }.getOrElse {
            Log.e("AppointmentFormRepo", "Lỗi khi lấy phiếu khám: ${it.message}")
            null
        }
    }
    override suspend fun getLatestAppointmentId(): Int? {
        return appointmentFormDao.getLatestAppointmentId()

    }

}

