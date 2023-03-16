package com.example.softwareestimation.employee_details_edit

import com.example.softwareestimation.data.db.employees.Employee
import javax.inject.Inject

class EmployeeDetailsEditInteractor @Inject constructor(
    private val repository: EmployeeDetailsEditRepository
) : EmployeeDetailsEditUseCase {

    override suspend fun getEmployee(guid: String?): Employee? {
        if (guid == null || guid.isBlank()) {
            return null
        } else {
            return repository.getEmployee(guid)
        }
    }

}