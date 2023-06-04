package com.example.softwareestimation.di

import android.content.Context
import androidx.room.Room
import com.example.softwareestimation.data.db.*
import com.example.softwareestimation.data.db.employees.EmployeeDao
import com.example.softwareestimation.data.db.estimated_project.EstimatedProjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SoftwareEstimationDatabase {
        return Room.databaseBuilder(
            context,
            SoftwareEstimationDatabase::class.java,
            DB_NAME
        )
            .build()
    }

    @Provides
    fun provideEstimatedProjectDao(
        softwareEstimationDatabase: SoftwareEstimationDatabase
    ): EstimatedProjectDao {
        return softwareEstimationDatabase.estimatedProjectDao()
    }

    @Provides
    fun provideProjectPercentSpreadDao(
        softwareEstimationDatabase: SoftwareEstimationDatabase
    ): ProjectPercentSpreadForTypesDao {
        return softwareEstimationDatabase.projectPercentSpreadDao()
    }

    @Provides
    fun provideEmployeeDao(
        softwareEstimationDatabase: SoftwareEstimationDatabase
    ): EmployeeDao {
        return softwareEstimationDatabase.employeeDao()
    }

    private companion object {
        const val DB_NAME = "softwareEstimationDB"
    }
}