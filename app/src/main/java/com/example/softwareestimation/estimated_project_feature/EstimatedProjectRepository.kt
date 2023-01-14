package com.example.softwareestimation.estimated_project_feature

import com.example.softwareestimation.data.db.EstimatedProject
import com.example.softwareestimation.data.db.EstimatedProjectDao
import javax.inject.Inject

class EstimatedProjectRepository @Inject constructor(
    private val estimatedProjectDao: EstimatedProjectDao
) {
    suspend fun getEstimatedProject(projectName: String): EstimatedProject {
        return estimatedProjectDao.getEstimatedProjectByName(projectName)
    }
}