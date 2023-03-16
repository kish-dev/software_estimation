package com.example.softwareestimation.employee_details_edit_feature

import com.example.softwareestimation.data.db.employees.Employee
import javax.inject.Inject

class EmployeeDetailsEditInteractor @Inject constructor(
    private val repository: EmployeeDetailsEditRepository
) : EmployeeDetailsEditUseCase {

    override suspend fun getEmployee(guid: String?): Employee? {
        return if (guid == null || guid.isBlank()) {
            null
        } else {
            repository.getEmployee(guid)
        }
    }

    override suspend fun updateEmployee(employee: Employee) =
        repository.updateEmployee(employee)

}