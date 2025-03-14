package com.example.healthy_diagnosis.di
import com.example.healthy_diagnosis.data.datasources.remote.ApiService
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.domain.repositories.FirebaseAuthRepository
import com.example.healthy_diagnosis.data.datasources.local.AppDatabase
import com.example.healthy_diagnosis.data.datasources.remote.EducationApiService
import com.example.healthy_diagnosis.data.datasources.remote.PatientApiService
import com.example.healthy_diagnosis.data.datasources.remote.PhysicianApiService
import com.example.healthy_diagnosis.data.datasources.remote.SpecializationApiService
import com.example.healthy_diagnosis.domain.repositories.EducationRepository
import com.example.healthy_diagnosis.domain.repositories.PatientRepository
import com.example.healthy_diagnosis.domain.repositories.PhysicianRepository
import com.example.healthy_diagnosis.domain.repositories.SpecializationRepository
import com.example.healthy_diagnosis.infrastructure.repositories.AccountRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.EducationRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.PatientRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.PhysicianRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.SpecializationRepositoryImpl
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

    @Provides
    @Singleton
    fun provideEducationRepository(
        database: AppDatabase,
        educationService: EducationApiService
    ): EducationRepository{
        return EducationRepositoryImpl(database.educationDao(), educationService)
    }

    @Provides
    @Singleton
    fun provideSpecializationRepository(
        database: AppDatabase,
        specializationApiService: SpecializationApiService
    ): SpecializationRepository {
        return SpecializationRepositoryImpl(database.specializationDao(), specializationApiService)
    }


    @Provides
    @Singleton
    fun providePhysicianRepository(
        database: AppDatabase,
        physicianApiService: PhysicianApiService
    ): PhysicianRepository {
        return PhysicianRepositoryImpl(database.physicianDao(), physicianApiService)
    }

    @Provides
    @Singleton
    fun providePatientRepository(
        database: AppDatabase,
        patientApiService: PatientApiService
    ): PatientRepository {
        return PatientRepositoryImpl(database.patientDao(),patientApiService)
    }
}