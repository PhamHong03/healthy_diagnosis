package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.ImagesDao
import com.example.healthy_diagnosis.data.datasources.remote.ImagesApiService
import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.domain.repositories.ImagesRepository
import com.example.healthy_diagnosis.domain.usecases.images.ImageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesDao: ImagesDao,
    private val imagesApiService: ImagesApiService
) : ImagesRepository{

    override suspend fun getImageById(imageId: Int): ImagesEntity? {
        val response = imagesApiService.getImageById(imageId)
        return if (response.isSuccessful) response.body() else null
    }

    override suspend fun updateImageEntity(imagesEntity: ImagesEntity) {
        imagesDao.update(imagesEntity)
    }

    override suspend fun uploadImage(
        image: MultipartBody.Part,
        physicianId: RequestBody,
        appointmentId: RequestBody,
        diseasesId: RequestBody
    ): Response<ImageResponse> {
        return try {
            val response = imagesApiService.uploadImage(image, physicianId, appointmentId, diseasesId)
            Log.d("Upload", "Upload response: $response")
            response
        } catch (e: Exception) {
            Log.e("Upload", "Upload exception: ${e.message}", e)
            throw e // ném lại exception để ViewModel xử lý
        }
    }

//    override suspend fun getAllImages(): List<ImagesEntity> {
//        return try {
//            imagesApiService.getImages()
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
    override suspend fun getAllImages(): List<ImagesEntity> {
//        return try {
//            val images = imagesDao.getAllImages()
//            Log.d("ImagesRepositoryImpl", "Fetched ${images.size} images from Room")
//            images
//        } catch (e: Exception) {
//            Log.e("ImagesRepositoryImpl", "Error fetching images from Room: ${e.message}")
//            emptyList()
//        }
        return runCatching {
            val images = imagesApiService.getImages()
            imagesDao.insertAll(images)
            images
        }.getOrElse {
            Log.e("ImagesRepo", "Lỗi kết nối API:: ${it.message}")
            imagesDao.getAllImages()
        }
    }

    override suspend fun insertImages(images: List<ImagesEntity>) {
        Log.d("ImagesRepositoryImpl", "Inserting ${images.size} images into Room")
        imagesDao.insertImages(images)
    }

    override suspend fun getImages(): List<ImagesEntity> {
        return imagesDao.getAllImages()
    }


}