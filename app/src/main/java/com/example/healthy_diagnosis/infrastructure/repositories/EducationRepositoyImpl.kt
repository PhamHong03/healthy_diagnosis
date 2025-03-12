package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.EducationDao
import com.example.healthy_diagnosis.data.datasources.remote.EducationApiService
import com.example.healthy_diagnosis.data.models.EducationEntity
import com.example.healthy_diagnosis.domain.repositories.EducationRepository
import javax.inject.Inject

class EducationRepositoryImpl @Inject constructor(
    private val educationDao: EducationDao,
    private val educationApiService: EducationApiService
) : EducationRepository{
    override suspend fun insertEducation(educationEntity: EducationEntity) {
        educationDao.insertEducation(educationEntity)
        try {
            val response = educationApiService.insertEducation(educationEntity)
            if (response.isSuccessful) {
                Log.d("EducationRepo", "Dữ liệu đã được gửi lên Flask server.")
            } else {
                Log.e("EducationRepo", "Lỗi khi gửi dữ liệu lên server: ${response.code()} - ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("EducationRepo", "Lỗi kết nối API: ${e.message}")
        }
    }

    override suspend fun getAllEducation(): List<EducationEntity> {
        return educationDao.getAllEducation()
    }

    override suspend fun deleteEducation(id: Int) {
        educationDao.deleteEducation(id)
        try {
            val response = educationApiService.deleteEducation(id)
            if (response.isSuccessful) {
                Log.d("EducationRepo", "Xóa thành công")
            } else {
                Log.e("EducationRepo", "Lỗi khi gửi dữ liệu lên server: ${response.code()} - ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("EducationRepo", "Lỗi kết nối API: ${e.message}")
        }
    }
}