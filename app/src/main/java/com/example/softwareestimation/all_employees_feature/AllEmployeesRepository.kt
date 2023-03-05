package com.example.softwareestimation.all_employees_feature

import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeDao
import javax.inject.Inject

class AllEmployeesRepository @Inject constructor(
    private val employeeDao: EmployeeDao
) {

    suspend fun getAllEmployees(): List<Employee> =
        employeeDao.getAllEmployees()

    suspend fun getAllEmployees(name: String): List<Employee> =
        employeeDao.getAllEmployeesByName(name)
}