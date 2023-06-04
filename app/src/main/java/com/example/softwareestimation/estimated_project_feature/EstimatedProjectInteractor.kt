package com.example.softwareestimation.estimated_project_feature

import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypes
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.data.db.employees.Employee
import javax.inject.Inject

class EstimatedProjectInteractor @Inject constructor(
    private val estimatedProjectRepository: EstimatedProjectRepository
) : EstimatedProjectUseCase {

    override suspend fun getEstimatedProject(projectName: String): EstimatedProject {
        return estimatedProjectRepository.getEstimatedProject(projectName)
    }

    override suspend fun getProjectPercentSpread(type: ProjectTypes): ProjectPercentSpreadForTypes {
        return estimatedProjectRepository.getProjectPercentSpreadForTypes(type)
    }

    override suspend fun getEmployees(): List<Employee> {
        return estimatedProjectRepository.getEmployees()
    }

    override suspend fun uploadNewEmployees(employees: List<Employee>) {
        return estimatedProjectRepository.uploadNewEmployees(employees)
    }

    override suspend fun uploadEstimatedProjectWithNewGeneratedTimeDiagram(
        estimatedProject: EstimatedProject
    ) {
        return estimatedProjectRepository
            .uploadEstimatedProjectWithNewGeneratedTimeDiagram(estimatedProject)
    }
}