package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.domain.usecases.images.ImageResponse
import okhttp3.MultipartBody
import java.io.File

interface ImagesRepository {
    suspend fun uploadImage(image: MultipartBody.Part, physicianId: Int, appointmentId: Int, diseasesId: Int?): Boolean

    suspend fun getAllImages(): List<ImagesEntity>
    suspend fun insertImages(images: List<ImagesEntity>)
    suspend fun getImages(): List<ImagesEntity>
    suspend fun getImageById(imageId: Int): ImagesEntity?
}
