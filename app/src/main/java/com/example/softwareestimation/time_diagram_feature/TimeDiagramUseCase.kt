package com.example.softwareestimation.time_diagram_feature

import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypes
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject

interface TimeDiagramUseCase {
    suspend fun getEmployees(): List<Employee>
    suspend fun uploadNewEmployees(employees: List<Employee>)
    suspend fun getProjectPercentSpread(type: ProjectTypes): ProjectPercentSpreadForTypes
    suspend fun getTimeDiagram(estimatedProject: EstimatedProject): TimeDiagramDto
}