package com.example.healthy_diagnosis.di

import com.example.healthy_diagnosis.data.datasources.remote.ApiService
import com.example.healthy_diagnosis.data.datasources.remote.ApplicationFormApiService
import com.example.healthy_diagnosis.data.datasources.remote.AppointmentFormApiService
import com.example.healthy_diagnosis.data.datasources.remote.CategoryDiseaseApiService
import com.example.healthy_diagnosis.data.datasources.remote.DiseaseApiService
import com.example.healthy_diagnosis.data.datasources.remote.EducationApiService
import com.example.healthy_diagnosis.data.datasources.remote.ImagesApiService
import com.example.healthy_diagnosis.data.datasources.remote.MedicalHistoryApiService
import com.example.healthy_diagnosis.data.datasources.remote.PatientApiService
import com.example.healthy_diagnosis.data.datasources.remote.PhysicianApiService
import com.example.healthy_diagnosis.data.datasources.remote.RoomApiService
import com.example.healthy_diagnosis.data.datasources.remote.SpecializationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    private val BASE_URL = "http://10.0.2.2:5000"
    private val BASE_URL = "http://192.168.1.9:5000/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory((GsonConverterFactory.create()))
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEducationService(retrofit: Retrofit): EducationApiService {
        return retrofit.create(EducationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSpecializationService(retrofit: Retrofit): SpecializationApiService {
        return retrofit.create(SpecializationApiService::class.java)
    }


    @Provides
    @Singleton
    fun providePhysicianService(retrofit: Retrofit): PhysicianApiService {
        return retrofit.create(PhysicianApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePatientService(retrofit: Retrofit): PatientApiService {
        return retrofit.create(PatientApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomService(retrofit: Retrofit): RoomApiService {
        return retrofit.create(RoomApiService::class.java)
    }

     @Provides
    @Singleton
    fun provideMedicalHistoryService(retrofit: Retrofit): MedicalHistoryApiService {
        return retrofit.create(MedicalHistoryApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApplicationFormService(retrofit: Retrofit): ApplicationFormApiService {
        return retrofit.create(ApplicationFormApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppointmentFormService(retrofit: Retrofit): AppointmentFormApiService {
        return retrofit.create(AppointmentFormApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImagesService(retrofit: Retrofit): ImagesApiService {
        return retrofit.create(ImagesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryDisease(retrofit: Retrofit): CategoryDiseaseApiService {
        return retrofit.create(CategoryDiseaseApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDisease(retrofit: Retrofit) : DiseaseApiService {
        return retrofit.create(DiseaseApiService::class.java)
    }

}