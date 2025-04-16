package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.CategoryDiseaseDao
import com.example.healthy_diagnosis.data.datasources.remote.CategoryDiseaseApiService
import com.example.healthy_diagnosis.data.models.CategoryDiseaseEntity
import com.example.healthy_diagnosis.domain.repositories.CategoryDiseaseRepository
import javax.inject.Inject

class CategoryDiseaseRepositoryImpl @Inject constructor(
    private val categoryDiseaseDao: CategoryDiseaseDao,
    private val categoryDiseaseApiService: CategoryDiseaseApiService
) : CategoryDiseaseRepository{
    override suspend fun getAllCategoryDisease(): List<CategoryDiseaseEntity> {
        return runCatching {
            val categoryDisease = categoryDiseaseApiService.getAllCategoryDisease()
            Log.d("DiseaseRepo", "Dữ liệu từ API: $categoryDisease")
            categoryDiseaseDao.insertAll(categoryDisease)
            categoryDisease
        }.getOrElse {
            Log.e("CategoryRepo", "Lỗi kết nối API: ${it.message}")
            categoryDiseaseDao.getAllCategoryDisease()
        }
    }
}