package com.example.softwareestimation.estimated_project_feature

import com.example.softwareestimation.data.db.*
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeDao
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import com.example.softwareestimation.data.db.estimated_project.EstimatedProjectDao
import javax.inject.Inject

class EstimatedProjectRepository @Inject constructor(
    private val estimatedProjectDao: EstimatedProjectDao,
    private val employeeDao: EmployeeDao,
    private val projectPercentSpreadForTypesDao: ProjectPercentSpreadForTypesDao,
) {
    suspend fun getEstimatedProject(projectName: String): EstimatedProject {
        return estimatedProjectDao.getEstimatedProjectByName(projectName)
    }

    suspend fun getProjectPercentSpreadForTypes(type: ProjectTypes): ProjectPercentSpreadForTypes {
        return projectPercentSpreadForTypesDao.getProjectPercentByType(type)
    }

    suspend fun getEmployees(): List<Employee> {
        return employeeDao.getAllEmployees()
    }

    suspend fun uploadNewEmployees(employees: List<Employee>) {
        return employees.forEach {
            employeeDao.addEmployee(it)
        }
    }

    suspend fun uploadEstimatedProjectWithNewGeneratedTimeDiagram(estimatedProject: EstimatedProject) {
        return estimatedProjectDao.addEstimatedProject(estimatedProject)
    }
}