package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.PhysicianDao
import com.example.healthy_diagnosis.data.datasources.remote.PhysicianApiService
import com.example.healthy_diagnosis.data.models.EducationEntity
import com.example.healthy_diagnosis.data.models.PhysicianEntity
import com.example.healthy_diagnosis.data.models.SpecializationEntity
import com.example.healthy_diagnosis.domain.repositories.PhysicianRepository
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
            physicianDao.insertAll(physicians) // Lưu vào Room
            physicians // Trả về danh sách từ API
        }.getOrElse {
            Log.e("PhysicianRepo", "Lỗi kết nối API: ${it.message}")
            physicianDao.getAllPhysician()
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

}