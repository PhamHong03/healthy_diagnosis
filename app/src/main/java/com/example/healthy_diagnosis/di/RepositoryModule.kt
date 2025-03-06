package com.example.healthy_diagnosis.di
import com.example.healthy_diagnosis.data.entities.Account
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.infrastructure.datasources.AccountDAO
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
    fun provideAccountRepository(accountDAO: AccountDAO): AccountRepository {
        return AccountRepositoryImpl(accountDAO)
    }
}