package com.example.softwareestimation.time_diagram_feature

import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypes
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypesDao
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeDao
import javax.inject.Inject

class TimeDiagramRepository @Inject constructor(
    private val employeeDao: EmployeeDao,
    private val projectPercentSpreadForTypesDao: ProjectPercentSpreadForTypesDao,
) {

    suspend fun getEmployees(): List<Employee> {
        return employeeDao.getAllEmployees()
    }

    suspend fun uploadNewEmployees(employees: List<Employee>) {
        return employees.forEach {
            employeeDao.addEmployee(it)
        }
    }

    suspend fun getProjectPercentSpreadForTypes(type: ProjectTypes): ProjectPercentSpreadForTypes {
        return projectPercentSpreadForTypesDao.getProjectPercentByType(type)
    }
}