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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

//    @Provides
//    fun provideAccountDAO(database: AppDatabase): AccountDAO {
//        return database.accountDAO()
//    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountDAO: AccountDAO,
        apiService: ApiService
    ): AccountRepository {
        return AccountRepositoryImpl(accountDAO, apiService)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://your-api-url.com/") // ⚠️ Cần thay thế bằng URL API thực tế
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
