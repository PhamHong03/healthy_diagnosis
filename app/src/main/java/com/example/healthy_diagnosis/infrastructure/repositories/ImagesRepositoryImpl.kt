package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.ImagesDao
import com.example.healthy_diagnosis.data.datasources.remote.ImagesApiService
import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.domain.repositories.ImagesRepository
import com.example.healthy_diagnosis.domain.usecases.images.ImageResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesDao: ImagesDao,
    private val imagesApiService: ImagesApiService
) : ImagesRepository{
//    override suspend fun uploadImage(image: MultipartBody.Part, physicianId: Int, appointmentId: Int, diseasesId: Int?): Boolean {
//        val response = imagesApiService.uploadImage(image, physicianId, appointmentId, diseasesId)
//        return response.isSuccessful
//    }
    override suspend fun uploadImage(
        image: MultipartBody.Part,
        physicianId: Int,
        appointmentId: Int,
        diseasesId: Int?
    ): Boolean {
        return try {
            val response = imagesApiService.uploadImage(image, physicianId, appointmentId, diseasesId)
            if (response.isSuccessful) {
                Log.d("Upload", "Image uploaded successfully")
                true
            } else {
                Log.e("Upload", "Failed to upload image: ${response.code()} - ${response.message()}")
                false
            }
        } catch (e: Exception) {
            Log.e("Upload", "Upload failed: ${e.message}", e)
            false
        }
    }


    override suspend fun getImageById(imageId: Int): ImagesEntity? {
        val response = imagesApiService.getImageById(imageId)
        return if (response.isSuccessful) response.body() else null
    }

    override suspend fun updateImageEntity(imagesEntity: ImagesEntity) {
        imagesDao.update(imagesEntity)
    }


    override suspend fun getAllImages(): List<ImagesEntity> {
        return try {
            imagesApiService.getImages()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun insertImages(images: List<ImagesEntity>) {
        imagesDao.insertImages(images)
    }

    override suspend fun getImages(): List<ImagesEntity> {
        return imagesDao.getAllImages()
    }


}