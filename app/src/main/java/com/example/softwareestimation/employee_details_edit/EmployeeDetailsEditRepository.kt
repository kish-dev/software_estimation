package com.example.softwareestimation.employee_details_edit

import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeDao
import javax.inject.Inject

class EmployeeDetailsEditRepository @Inject constructor(private val employeeDao: EmployeeDao) {

    suspend fun getEmployee(guid: String): Employee? =
        employeeDao.getEmployee(guid)
}