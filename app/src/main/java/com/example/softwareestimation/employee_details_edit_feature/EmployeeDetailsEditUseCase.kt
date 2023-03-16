package com.example.softwareestimation.employee_details_edit_feature

import com.example.softwareestimation.data.db.employees.Employee

interface EmployeeDetailsEditUseCase {

    suspend fun getEmployee(guid: String?): Employee?

    suspend fun updateEmployee(employee: Employee)
}