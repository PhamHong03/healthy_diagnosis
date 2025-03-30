package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.domain.usecases.images.ImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ImagesApiService {

    @Multipart
    @POST("upload_image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("physician_id") physicianId: Int,
        @Part("appointment_id") appointmentId: Int,
        @Part("diseases_id") diseasesId: Int?
    ): Response<ResponseBody>

    @GET("get_images")
    suspend fun getImages(): List<ImagesEntity>


    @GET("images/{id}")
    suspend fun getImageById(@Path("id") imageId: Int): Response<ImagesEntity>

}