package com.example.softwareestimation.employee_details_feature

import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeDao
import javax.inject.Inject

class EmployeeDetailsRepository @Inject constructor(private val employeeDao: EmployeeDao) {

    suspend fun getEmployee(guid: String): Employee? =
        employeeDao.getEmployee(guid)
}