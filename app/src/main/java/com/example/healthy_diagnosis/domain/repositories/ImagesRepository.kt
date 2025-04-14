package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.domain.usecases.images.ImageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface ImagesRepository {
    suspend fun uploadImage(
        image: MultipartBody.Part,
        physicianId: RequestBody,
        appointmentId: RequestBody,
        diseasesId: RequestBody?
    ): Boolean
    suspend fun getAllImages(): List<ImagesEntity>
    suspend fun insertImages(images: List<ImagesEntity>)
    suspend fun getImages(): List<ImagesEntity>
    suspend fun getImageById(imageId: Int): ImagesEntity?

    suspend fun updateImageEntity(imagesEntity: ImagesEntity)
}
