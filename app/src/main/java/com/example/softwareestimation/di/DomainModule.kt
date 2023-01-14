package com.example.softwareestimation.di

import com.example.softwareestimation.data.FillFormRepository
import com.example.softwareestimation.data.db.EstimatedProjectDao
import com.example.softwareestimation.estimated_project_feature.EstimatedProjectInteractor
import com.example.softwareestimation.estimated_project_feature.EstimatedProjectRepository
import com.example.softwareestimation.estimated_project_feature.EstimatedProjectUseCase
import com.example.softwareestimation.fill_project_form_feature.FillFormInteractor
import com.example.softwareestimation.fill_project_form_feature.FillFormUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideFillFormUseCase(
        fillFormRepository: FillFormRepository
    ): FillFormUseCase {
        return FillFormInteractor(fillFormRepository)
    }

    @Singleton
    @Provides
    fun provideEstimatedProjectUseCase(
        estimatedProjectRepository: EstimatedProjectRepository
    ): EstimatedProjectUseCase {
        return EstimatedProjectInteractor(estimatedProjectRepository)
    }

    @Singleton
    @Provides
    fun provideEstimatedProjectRepository(
        estimatedProjectDao: EstimatedProjectDao
    ): EstimatedProjectRepository {
        return EstimatedProjectRepository(estimatedProjectDao)
    }

    @Singleton
    @Provides
    fun provideFillFormRepository(estimatedProjectDao: EstimatedProjectDao): FillFormRepository {
        return FillFormRepository(estimatedProjectDao)
    }


}