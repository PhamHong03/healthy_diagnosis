package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.PatientDao
import com.example.healthy_diagnosis.data.datasources.remote.PatientApiService
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.domain.repositories.PatientRepository

class PatientRepositoryImpl(
    private val patientDao: PatientDao,
    private val patientApiService: PatientApiService
) : PatientRepository{
    override suspend fun insertPatient(patientEntity: PatientEntity) {
        patientDao.insertPatient(patientEntity)
        try {
            val response = patientApiService.insertPatient(patientEntity)
            if (response.isSuccessful) {
                Log.d("PatientRepo", "Dữ liệu đã gửi lên flask")
            }else {
                Log.e("PatientRepo", "Lỗi dữ liệu lên server: ${response.message()}")
            }
        }catch (e:Exception) {
            Log.e("Patients", "Lỗi kết nối API: ${e}")
        }
    }

    override suspend fun getAllPatient(): List<PatientEntity> {
        return kotlin.runCatching {
            val patients = patientApiService.getAllPatients()
            patientDao.insertAll(patients)
            patients
        }.getOrElse {
            Log.e("PatientRepo", "Lỗi kết nối API: ${it.message}")
            patientDao.getAllPatient()
        }
    }

    override suspend fun deletePatient(id: Int) {
        try {
            val response = patientApiService.deletePatient(id)
            if(response.isSuccessful) {
                Log.d("PhysicianRepo", "Xóa thành công")
            }else{
                Log.d("PhysicianRepo", "Lỗi gửi dữ liệu lên server:  ${response.code()} - \${response.errorBody()?.string()")
            }
        }catch (e:Exception) {
            Log.e("PhysicianRepo", " Lỗi kết nối API: ${e.message}")
        }
    }

    override suspend fun getPatientByAccountId(accountId: Int): PatientEntity? {
        return try {
            val patient = patientApiService.getPatientByAccountId(accountId)
            patient?.let { patientDao.insertPatient(it) } // Chỉ lưu nếu không null
            patient
        } catch (e: Exception) {
            Log.e("PatientRepo", "Lỗi kết nối API: ${e.message}")
            patientDao.getPatientByAccountId(accountId) // Lấy dữ liệu từ cache
        }
    }


}