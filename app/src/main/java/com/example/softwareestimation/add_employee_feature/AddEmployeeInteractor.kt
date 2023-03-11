package com.example.softwareestimation.add_employee_feature

import com.example.softwareestimation.data.db.employees.Employee
import javax.inject.Inject

class AddEmployeeInteractor @Inject constructor(private val repository: AddEmployeeRepository) :
    AddEmployeeUseCase {

    override suspend fun addEmployee(employee: Employee) =
        repository.addEmployee(employee)

}