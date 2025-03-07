package com.example.healthy_diagnosis.di

import android.content.Context
import com.example.healthy_diagnosis.data.api.ApiService
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.domain.usecases.register.RegisterUsecase
import com.example.healthy_diagnosis.infrastructure.datasources.AccountDAO
import com.example.healthy_diagnosis.infrastructure.datasources.AppDatabase
import com.example.healthy_diagnosis.infrastructure.repositories.AccountRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class
    ]
)
@InstallIn(SingletonComponent::class)
object AppModule {}
