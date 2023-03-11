package com.example.softwareestimation.add_employee_feature

import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeDao
import javax.inject.Inject

class AddEmployeeRepository @Inject constructor(private val employeeDao: EmployeeDao) {

    suspend fun addEmployee(employee: Employee) =
        employeeDao.addEmployee(employee)
}