package com.example.softwareestimation.di

import com.example.softwareestimation.all_projects_feature.AllProjectsInteractor
import com.example.softwareestimation.all_projects_feature.AllProjectsRepository
import com.example.softwareestimation.all_projects_feature.AllProjectsUseCase
import com.example.softwareestimation.all_projects_feature.AllProjectsViewHolder
import com.example.softwareestimation.data.FillFormRepository
import com.example.softwareestimation.data.db.estimated_project.EstimatedProjectDao
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypesDao
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
    fun provideAllProjectsUseCase(
        allProjectsRepository: AllProjectsRepository
    ): AllProjectsUseCase {
        return AllProjectsInteractor(allProjectsRepository)
    }

    @Singleton
    @Provides
    fun provideEstimatedProjectRepository(
        estimatedProjectDao: EstimatedProjectDao,
        projectPercentSpreadForTypesDao: ProjectPercentSpreadForTypesDao,
    ): EstimatedProjectRepository {
        return EstimatedProjectRepository(
            estimatedProjectDao,
            projectPercentSpreadForTypesDao
        )
    }

    @Singleton
    @Provides
    fun provideFillFormRepository(estimatedProjectDao: EstimatedProjectDao): FillFormRepository {
        return FillFormRepository(estimatedProjectDao)
    }

    @Singleton
    @Provides
    fun provideAllProjectsRepository(
        estimatedProjectDao: EstimatedProjectDao
    ): AllProjectsRepository {
        return AllProjectsRepository(estimatedProjectDao)
    }


}