package com.example.softwareestimation.employee_details_feature

import com.example.softwareestimation.data.db.employees.Employee

interface EmployeeDetailsUseCase {

    suspend fun getEmployee(guid: String?): Employee?
}