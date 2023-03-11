package com.example.softwareestimation.add_employee_feature

import com.example.softwareestimation.data.db.employees.Employee

interface AddEmployeeUseCase {

    suspend fun addEmployee(employee: Employee)
}