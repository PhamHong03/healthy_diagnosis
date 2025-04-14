package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.domain.usecases.images.ImageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Part file: MultipartBody.Part,
        @Part("physician_id") physicianId: RequestBody,
        @Part("appointment_id") appointmentId: RequestBody,
        @Part("diseases_id") diseasesId: RequestBody?
    ): Response<ImageResponse>


    @GET("get_images")
    suspend fun getImages(): List<ImagesEntity>


    @GET("images/{id}")
    suspend fun getImageById(@Path("id") imageId: Int): Response<ImagesEntity>

}