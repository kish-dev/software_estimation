package com.example.softwareestimation.all_employees_feature

import com.example.softwareestimation.data.db.employees.Employee

interface AllEmployeesUseCase {

    suspend fun getAllEmployees(): List<Employee>

    suspend fun getAllEmployees(name: String): List<Employee>
}