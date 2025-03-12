package com.example.healthy_diagnosis.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class
    ]
)
@InstallIn(SingletonComponent::class)
object AppModule {}
