package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.PhysicianDao
import com.example.healthy_diagnosis.data.datasources.remote.PhysicianApiService
import com.example.healthy_diagnosis.data.models.EducationEntity
import com.example.healthy_diagnosis.data.models.PhysicianEntity
import com.example.healthy_diagnosis.data.models.SpecializationEntity
import com.example.healthy_diagnosis.domain.repositories.PhysicianRepository
import com.example.healthy_diagnosis.domain.usecases.patient.PatientWithApplicationDate
import javax.inject.Inject

class PhysicianRepositoryImpl @Inject constructor(
    private val physicianDao: PhysicianDao,
    private val physicianApiService: PhysicianApiService,
): PhysicianRepository {
    override suspend fun insertPhysician(physicianEntity: PhysicianEntity) {
        physicianDao.insertPhysician(physicianEntity)

        try {
            val response = physicianApiService.insertPhysician(physicianEntity)
            if(response.isSuccessful) {
                Log.d("PhycisianRepo", "Dữ liệu đã gửi lên Flask")

            }else{
                Log.e("PhysicianRepo", "Lỗi dữ liệu gửi lên Server:  ${response.code()} - \${response.errorBody()?.string()")
            }

        }catch (e: Exception) {
            Log.e("PhysicianRepo", "Lỗi kết nối API : ${e.message}")
        }
    }

    override suspend fun getAllPhysician(): List<PhysicianEntity> {
        return runCatching {
            val physicians = physicianApiService.getAllPhysician() // Gọi API lấy danh sách bác sĩ
            physicianDao.insertAll(physicians)
//            physicianDao.getAllPhysician() // Luôn lấy từ Room
            physicians
        }.getOrElse {
            Log.e("PhysicianRepo", "Lỗi kết nối API: ${it.message}")
            physicianDao.getAllPhysician() // Lấy từ Room nếu lỗi API
        }
    }

    override suspend fun deletePhysician(id: Int) {
        try {
            val response = physicianApiService.deletePhysican(id)
            if(response.isSuccessful) {
                Log.d("PhysicianRepo", "Xóa thành công")
            }else{
                Log.d("PhysicianRepo", "Lỗi gửi dữ liệu lên server:  ${response.code()} - \${response.errorBody()?.string()")
            }
        }catch (e:Exception) {
            Log.e("PhysicianRepo", " Lỗi kết nối API: ${e.message}")
        }
    }

    override suspend fun getPhysicianByAccountId(accountId: Int): PhysicianEntity? {
        return try {
            val physician = physicianApiService.getPhysicianByAccountId(accountId)
            physician?.let {
                physicianDao.insertPhysician(it)
                physician
            }
        }catch (e:Exception){
            Log.e("PhysicianRepo", "Lỗi kết nối API: ${e.message}")

            physicianDao.getPhysicianById(accountId)
        }
    }

    override suspend fun getPhysicianIdByAccountId(accountId: Int): Int? {
        return physicianDao.getPhysicianIdByAccountId(accountId)
    }

    override suspend fun checkPhysicianExists(accountId: Int): Boolean {
        return try {
            val response = physicianApiService.getPhysicianByAccountId(accountId)
            response?.id != null  // Nếu tồn tại bác sĩ, trả về true
        } catch (e: Exception) {
            false // Nếu lỗi hoặc không có dữ liệu, trả về false
        }
    }

    override suspend fun getPatientsByPhysician(accountId: Int): List<PatientWithApplicationDate> {
        return physicianApiService.getPatientsByPhysician(accountId)
    }


}