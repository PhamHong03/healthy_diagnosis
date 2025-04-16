package com.example.healthy_diagnosis.di
import com.example.healthy_diagnosis.data.datasources.remote.ApiService
import com.example.healthy_diagnosis.domain.repositories.AccountRepository
import com.example.healthy_diagnosis.domain.repositories.FirebaseAuthRepository
import com.example.healthy_diagnosis.data.datasources.local.AppDatabase
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
import com.example.healthy_diagnosis.domain.repositories.ApplicationFormRespository
import com.example.healthy_diagnosis.domain.repositories.AppointmentFormRepository
import com.example.healthy_diagnosis.domain.repositories.CategoryDiseaseRepository
import com.example.healthy_diagnosis.domain.repositories.DiagnosisRepository
import com.example.healthy_diagnosis.domain.repositories.DiseaseRepository
import com.example.healthy_diagnosis.domain.repositories.EducationRepository
import com.example.healthy_diagnosis.domain.repositories.ImagesRepository
import com.example.healthy_diagnosis.domain.repositories.MedicalHistoryRepository
import com.example.healthy_diagnosis.domain.repositories.PatientRepository
import com.example.healthy_diagnosis.domain.repositories.PhysicianRepository
import com.example.healthy_diagnosis.domain.repositories.RoomRepository
import com.example.healthy_diagnosis.domain.repositories.SpecializationRepository
import com.example.healthy_diagnosis.infrastructure.repositories.AccountRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.ApplicationFormRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.AppointmentFormRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.CategoryDiseaseRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.DiagnosisRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.DiseaseRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.EducationRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.ImagesRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.MedicalHistoryRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.PatientRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.PhysicianRepositoryImpl
import com.example.healthy_diagnosis.infrastructure.repositories.RoomRepositoryImpl
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
        return AccountRepositoryImpl(database.accountDao(), apiService, firebaseAuthRepository)
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

    @Provides
    @Singleton
    fun providedRoomRepository(
        database: AppDatabase,
        roomApiService: RoomApiService
    ): RoomRepository {
        return RoomRepositoryImpl(database.roomDao(), roomApiService)
    }
    @Provides
    @Singleton
    fun providedMedicalHistoryRepository(
        database: AppDatabase,
        medicalHistoryApiService: MedicalHistoryApiService
    ): MedicalHistoryRepository {
        return MedicalHistoryRepositoryImpl(database.medicalhistoryDao(), medicalHistoryApiService)
    }

    @Provides
    @Singleton
    fun provideApplicationFormRepository(
        database: AppDatabase,
        applicationFormApiService: ApplicationFormApiService
    ): ApplicationFormRespository {
        return ApplicationFormRepositoryImpl(database.applicationFormDao(), applicationFormApiService)
    }

    @Provides
    @Singleton
    fun provideAppointmentFormRepository(
        database: AppDatabase,
        appointmentFormApiService: AppointmentFormApiService
    ): AppointmentFormRepository {
        return AppointmentFormRepositoryImpl(database.appointmentFormDao(), appointmentFormApiService)
    }

    @Provides
    @Singleton
    fun provideImagesRepository(
        database: AppDatabase,
        imagesApiService: ImagesApiService
    ): ImagesRepository {
        return ImagesRepositoryImpl(database.imagesDao(), imagesApiService)
    }


    @Provides
    @Singleton
    fun provideCategoryDiseaseRepository(
        database: AppDatabase,
        categoryDiseaseApiService: CategoryDiseaseApiService
    ): CategoryDiseaseRepository {
        return CategoryDiseaseRepositoryImpl(database.categoryDiseaseDao(), categoryDiseaseApiService)
    }

    @Provides
    @Singleton
    fun provideDiseaseRepository(
        database: AppDatabase,
        diseaseApiService: DiseaseApiService
    ): DiseaseRepository {
        return DiseaseRepositoryImpl(database.diseaseDao(), diseaseApiService)
    }
    @Provides
    @Singleton
    fun provideDiagnosisRepository(
        database: AppDatabase
    ): DiagnosisRepository{
        return DiagnosisRepositoryImpl(database.diagnosisDao())
    }
}