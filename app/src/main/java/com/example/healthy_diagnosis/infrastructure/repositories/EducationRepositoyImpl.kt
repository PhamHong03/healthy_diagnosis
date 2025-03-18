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
        try {
            // Lưu vào Room bằng insertEducation()
            educationDao.insertEducation(educationEntity)
            Log.d("EducationRepo", "Đã lưu vào Room Database: $educationEntity")

            // Gửi dữ liệu lên Flask
            val response = educationApiService.insertEducation(educationEntity)
            if (response.isSuccessful) {
                Log.d("EducationRepo", "Dữ liệu đã được gửi lên Flask server.")
            } else {
                Log.e("EducationRepo", "Lỗi khi gửi dữ liệu lên server: ${response.code()} - ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("EducationRepo", "Lỗi khi lưu hoặc gửi dữ liệu: ${e.message}")
        }
    }
    override suspend fun getAllEducations(): List<EducationEntity> {
        return try {
            val localData = educationDao.getAllEducation()
            if (localData.isNotEmpty()) {
                Log.d("EducationRepo", "Lấy từ Room Database: $localData")
                return localData
            }

            val apiData = educationApiService.getAllEducations()
            Log.d("EducationRepo", "Dữ liệu lấy từ API: $apiData")

            if (apiData.isNotEmpty()) {
                try {
                    apiData.forEach { educationDao.insertEducation(it) }
                    Log.d("EducationRepo", "Đã lưu dữ liệu API vào Room Database")
                } catch (e: Exception) {
                    Log.e("EducationRepo", "Lỗi khi lưu vào Room: ${e.message}")
                }
            } else {
                Log.w("EducationRepo", "API trả về danh sách rỗng")
            }

            apiData
        } catch (e: Exception) {
            Log.e("EducationRepo", "Lỗi khi gọi API: ${e.message}")
            emptyList()
        }
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