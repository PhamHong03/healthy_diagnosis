package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.ApplicationFormDao
import com.example.healthy_diagnosis.data.datasources.remote.ApplicationFormApiService
import com.example.healthy_diagnosis.data.models.ApplicationFormEntity
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.domain.repositories.ApplicationFormRespository
import javax.inject.Inject

class ApplicationFormRepositoryImpl @Inject constructor(
    private val applicationFormDao: ApplicationFormDao,
    private val applicationFormApiService: ApplicationFormApiService
): ApplicationFormRespository {

    override suspend fun getAllApplicationForm(): List<ApplicationFormEntity> {
        return kotlin.runCatching {
            val response = applicationFormApiService.getAllApplicationForms()
            if (response.isSuccessful) {
                val applicationForms = response.body() ?: emptyList()
                applicationFormDao.insertAllApplicationForm(applicationForms)
                applicationForms
            } else {
                Log.e("ApplicationFormRepo", "Lỗi response: ${response.code()} - ${response.errorBody()?.string()}")
                applicationFormDao.getAllApplicationForm()
            }
        }.getOrElse {
            Log.e("ApplicationFormRepo", "Lỗi kết nối API: ${it.message}")
            applicationFormDao.getAllApplicationForm()
        }
    }



    override suspend fun insertApplicationForm(applicationFormEntity: ApplicationFormEntity) {
        applicationFormDao.insertApplicationForm(applicationFormEntity)

        try {
            val response = applicationFormApiService.insertApplicationForm(applicationFormEntity)
            if(response.isSuccessful){
                Log.d("ApplicationFormRepo", "Dữ liệu gửi lên flask")
            }else{
                Log.e("ApplicationFormRepo", "Lỗi dữ liệu lên server: ${response.code()} - \${response.errorBody()?.string()")
            }
        }catch (e: Exception) {
            Log.e("ApplicationFormRepo", "Lỗi kết nối API : ${e.message}")
        }
    }

    override suspend fun deleteApplicationForm(id: Int) {
        try {
            val response = applicationFormApiService.deleteApplication(id)
            if(response.isSuccessful) {
                Log.d("ApplicationFormRepo", "Xóa thành công")
            }else{
                Log.d("ApplicationFormRepo", "Lỗi gửi dữ liệu lên server:  ${response.code()} - \${response.errorBody()?.string()")
            }
        }catch (e:Exception){
            Log.e("ApplicationFormRepo", " Lỗi kết nối API: ${e.message}")
        }
    }

    override suspend fun getPatientByApplicationFormId(applicationFormId: Int): PatientEntity? {
        Log.d("ApplicationFormRepo", "Fetching from DB with applicationFormId: $applicationFormId")
        val patient = applicationFormDao.getPatientByApplicationFormId(applicationFormId)
        Log.d("ApplicationFormRepo", "Patient from DB: $patient")
        return patient

    }


}