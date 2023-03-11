package com.example.softwareestimation.di

import com.example.softwareestimation.add_employee_feature.AddEmployeeInteractor
import com.example.softwareestimation.add_employee_feature.AddEmployeeRepository
import com.example.softwareestimation.add_employee_feature.AddEmployeeUseCase
import com.example.softwareestimation.all_employees_feature.AllEmployeesInteractor
import com.example.softwareestimation.all_employees_feature.AllEmployeesRepository
import com.example.softwareestimation.all_employees_feature.AllEmployeesUseCase
import com.example.softwareestimation.all_projects_feature.AllProjectsInteractor
import com.example.softwareestimation.all_projects_feature.AllProjectsRepository
import com.example.softwareestimation.all_projects_feature.AllProjectsUseCase
import com.example.softwareestimation.data.FillFormRepository
import com.example.softwareestimation.data.db.estimated_project.EstimatedProjectDao
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypesDao
import com.example.softwareestimation.data.db.employees.EmployeeDao
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
    fun provideAllEmployeesUseCase(
        allEmployeesRepository: AllEmployeesRepository
    ): AllEmployeesUseCase {
        return AllEmployeesInteractor(allEmployeesRepository)
    }

    @Singleton
    @Provides
    fun provideAddEmployeesUseCase(
        addEmployeeRepository: AddEmployeeRepository
    ): AddEmployeeUseCase {
        return AddEmployeeInteractor(addEmployeeRepository)
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

    @Singleton
    @Provides
    fun provideAllEmployeesRepository(
        employeesDao: EmployeeDao
    ): AllEmployeesRepository {
        return AllEmployeesRepository(employeesDao)
    }

    @Singleton
    @Provides
    fun provideAddEmployeeRepository(
        employeesDao: EmployeeDao
    ): AddEmployeeRepository {
        return AddEmployeeRepository(employeesDao)
    }

}