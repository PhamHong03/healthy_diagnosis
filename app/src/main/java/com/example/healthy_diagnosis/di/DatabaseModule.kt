package com.example.healthy_diagnosis.di

import android.content.Context
import androidx.room.Room
import com.example.healthy_diagnosis.infrastructure.datasources.AccountDAO
import com.example.healthy_diagnosis.infrastructure.datasources.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context:Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }


    @Provides
    fun provideAccountDao(db:AppDatabase):AccountDAO{
        return db.accountDao()
    }
}