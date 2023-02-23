package com.example.softwareestimation.estimated_project_feature

import com.example.softwareestimation.data.db.EstimatedProject
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypes
import com.example.softwareestimation.data.db.ProjectTypes
import javax.inject.Inject

class EstimatedProjectInteractor @Inject constructor(
    private val estimatedProjectRepository: EstimatedProjectRepository
): EstimatedProjectUseCase {

    override suspend fun getEstimatedProject(projectName: String): EstimatedProject {
        return estimatedProjectRepository.getEstimatedProject(projectName)
    }

    override suspend fun getProjectPercentSpread(type: ProjectTypes): ProjectPercentSpreadForTypes {
        return estimatedProjectRepository.getProjectPercentSpreadForTypes(type)
    }

}