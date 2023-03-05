package com.example.softwareestimation.estimated_project_feature

import com.example.softwareestimation.data.db.*
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import com.example.softwareestimation.data.db.estimated_project.EstimatedProjectDao
import javax.inject.Inject

class EstimatedProjectRepository @Inject constructor(
    private val estimatedProjectDao: EstimatedProjectDao,
    private val projectPercentSpreadForTypesDao: ProjectPercentSpreadForTypesDao,
) {
    suspend fun getEstimatedProject(projectName: String): EstimatedProject {
        return estimatedProjectDao.getEstimatedProjectByName(projectName)
    }

    suspend fun getProjectPercentSpreadForTypes(type: ProjectTypes): ProjectPercentSpreadForTypes {
        return projectPercentSpreadForTypesDao.getProjectPercentByType(type)
    }
}