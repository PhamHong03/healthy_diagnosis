package com.example.healthy_diagnosis.di

import com.example.healthy_diagnosis.data.datasources.remote.ApiService
import com.example.healthy_diagnosis.data.datasources.remote.EducationApiService
import com.example.healthy_diagnosis.data.datasources.remote.PatientApiService
import com.example.healthy_diagnosis.data.datasources.remote.PhysicianApiService
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

//    private val BASE_URL = "http://192.168.1.7:5000"
    private val BASE_URL = "http://192.168.6.161:5000"

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
}