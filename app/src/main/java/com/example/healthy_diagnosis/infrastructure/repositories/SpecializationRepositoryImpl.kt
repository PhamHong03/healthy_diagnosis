package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.PhysicianDao
import com.example.healthy_diagnosis.data.datasources.local.SpecializationDao
import com.example.healthy_diagnosis.data.datasources.remote.PhysicianApiService
import com.example.healthy_diagnosis.data.datasources.remote.SpecializationApiService
import com.example.healthy_diagnosis.data.models.SpecializationEntity
import com.example.healthy_diagnosis.domain.repositories.SpecializationRepository
import javax.inject.Inject

class SpecializationRepositoryImpl @Inject constructor(
    private val specializationDao: SpecializationDao,
    private val specializationApiService: SpecializationApiService
) : SpecializationRepository{
    override suspend fun getSpecializations(): List<SpecializationEntity> {
        return try {
            val response = specializationApiService.getSpecializations()
            if (response.isSuccessful) {
                val specializations = response.body() ?: emptyList()
                specializationDao.insertSpecializations(specializations) // Lưu vào Room
                specializations
            } else {
                Log.e("SpecializationRepo", "Lỗi lấy danh sách chuyên môn: ${response.code()} - ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("SpecializationRepo", "Lỗi kết nối API (specializations): ${e.message}")
            emptyList()
        }
    }

}