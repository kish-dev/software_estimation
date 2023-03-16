package com.example.softwareestimation.employee_details_edit

import com.example.softwareestimation.data.db.employees.Employee

interface EmployeeDetailsEditUseCase {

    suspend fun getEmployee(guid: String?): Employee?
}