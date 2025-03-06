package com.example.healthy_diagnosis.di

import android.content.Context
import com.example.healthy_diagnosis.data.api.ApiService
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.infrastructure.datasources.AccountDAO
import com.example.healthy_diagnosis.infrastructure.datasources.AppDatabase
import com.example.healthy_diagnosis.infrastructure.repositories.AccountRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase
    {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideAccountRepository(
        accountDAO: AccountDAO,
        apiService: ApiService
    ): AccountRepository{
        return AccountRepositoryImpl(accountDAO, apiService)
    }
}