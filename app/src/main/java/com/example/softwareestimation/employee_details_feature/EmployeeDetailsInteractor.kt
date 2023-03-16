package com.example.softwareestimation.employee_details_feature

import com.example.softwareestimation.data.db.employees.Employee
import javax.inject.Inject

class EmployeeDetailsInteractor @Inject constructor(
    private val repository: EmployeeDetailsRepository
) : EmployeeDetailsUseCase {

    override suspend fun getEmployee(guid: String?): Employee? {
        if (guid == null || guid.isBlank()) {
            return null
        } else {
            return repository.getEmployee(guid)
        }
    }

}