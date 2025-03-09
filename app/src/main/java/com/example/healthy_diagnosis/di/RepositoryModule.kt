package com.example.healthy_diagnosis.di
import com.example.healthy_diagnosis.data.api.ApiService
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.domain.repositories.FirebaseAuthRepository
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
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAccountRepository(
        database: AppDatabase,
        apiService: ApiService,
        firebaseAuthRepository: FirebaseAuthRepository
    ): AccountRepository {
        return AccountRepositoryImpl(database, apiService, firebaseAuthRepository)
    }
}