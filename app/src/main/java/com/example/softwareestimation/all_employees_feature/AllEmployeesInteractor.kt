package com.example.softwareestimation.all_employees_feature

import com.example.softwareestimation.data.db.employees.Employee
import javax.inject.Inject

class AllEmployeesInteractor @Inject constructor(private val repository: AllEmployeesRepository) :
    AllEmployeesUseCase {

    override suspend fun getAllEmployees(): List<Employee> =
        repository.getAllEmployees()

    override suspend fun getAllEmployees(name: String): List<Employee> =
        repository.getAllEmployees("%${name}%")

}