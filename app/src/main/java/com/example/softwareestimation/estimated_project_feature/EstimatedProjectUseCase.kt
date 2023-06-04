package com.example.softwareestimation.estimated_project_feature

import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypes
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.time_diagram_feature.TimeDiagramDto

interface EstimatedProjectUseCase {
   suspend fun getEstimatedProject(projectName: String): EstimatedProject
   suspend fun getProjectPercentSpread(type: ProjectTypes): ProjectPercentSpreadForTypes
   suspend fun getEmployees(): List<Employee>
   suspend fun uploadNewEmployees(employees: List<Employee>)
   suspend fun uploadEstimatedProjectWithNewGeneratedTimeDiagram(estimatedProject: EstimatedProject)
}