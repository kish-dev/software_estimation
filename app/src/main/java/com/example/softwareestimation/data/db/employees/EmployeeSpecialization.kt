package com.example.softwareestimation.data.db.employees

data class EmployeeSpecialization(
    val sphere: EmployeeSpheres,
    val level: EmployeesLevels,
) {
    companion object {
        fun manager(): List<EmployeeSpecialization> {
            return listOf(
                EmployeeSpecialization(EmployeeSpheres.PROJECT_MANAGER, EmployeesLevels.INTERN),
                EmployeeSpecialization(EmployeeSpheres.PROJECT_MANAGER, EmployeesLevels.JUNIOR),
                EmployeeSpecialization(EmployeeSpheres.PROJECT_MANAGER, EmployeesLevels.MIDDLE),
                EmployeeSpecialization(EmployeeSpheres.PROJECT_MANAGER, EmployeesLevels.SENIOR),
                EmployeeSpecialization(EmployeeSpheres.PROJECT_MANAGER, EmployeesLevels.LEAD),
            )
        }
    }
}

