package com.example.softwareestimation.add_employee_feature

import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeSpecialization
import java.util.*

data class EmployeeVo(
    val name: String,
    val surname: String,
    val specializations: List<EmployeeSpecialization>,
) {
    companion object {
        fun baseState(): EmployeeVo {
            return EmployeeVo(
                name = "",
                surname = "",
                specializations = emptyList()
            )
        }
    }
}

fun EmployeeVo.toDomain() : Employee {
    return Employee(
        guid = UUID.randomUUID().toString(),
        name = this.name,
        surname = this.surname,
        specializations = this.specializations
    )
}