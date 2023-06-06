package com.example.softwareestimation.time_diagram_feature

import android.content.Context
import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypes
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeBusiness
import com.example.softwareestimation.data.db.employees.EmployeeSpheres
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import java.util.*
import javax.inject.Inject

class TimeDiagramInteractor @Inject constructor(
    private val timeDiagramRepository: TimeDiagramRepository,
    private val applicationContext: Context,
) : TimeDiagramUseCase {

    companion object {
        private const val ONE_DAY = 86_400_000
    }

    override suspend fun getEmployees(): List<Employee> {
        return timeDiagramRepository.getEmployees()
    }

    override suspend fun uploadNewEmployees(employees: List<Employee>) {
        return timeDiagramRepository.uploadNewEmployees(employees)
    }

    override suspend fun getProjectPercentSpread(type: ProjectTypes): ProjectPercentSpreadForTypes {
        return timeDiagramRepository.getProjectPercentSpreadForTypes(type)
    }

    override suspend fun getTimeDiagram(estimatedProject: EstimatedProject): TimeDiagramDto {
        return getTimeDiagram(
            employees = getEmployees(),
            estimatedProject = estimatedProject,
            projectPercentSpread = getProjectPercentSpread(estimatedProject.projectTypes)
        )
    }

    private suspend fun getTimeDiagram(
        employees: List<Employee>,
        estimatedProject: EstimatedProject,
        projectPercentSpread: ProjectPercentSpreadForTypes,
    ): TimeDiagramDto {

        val analyticsWeeks =
            estimatedProject.fullHumanMonth * (projectPercentSpread.analyticPercent
                ?: 0.0) / 100.0 * 4.0
        val androidWeeks =
            estimatedProject.fullHumanMonth * (projectPercentSpread.androidPercent
                ?: 0.0) / 100.0 * 4.0
        val frontendWeeks =
            estimatedProject.fullHumanMonth * (projectPercentSpread.frontendPercent
                ?: 0.0) / 100.0 * 4.0
        val backendWeeks =
            estimatedProject.fullHumanMonth * (projectPercentSpread.backendPercent
                ?: 0.0) / 100.0 * 4.0
        val iosWeeks =
            estimatedProject.fullHumanMonth * (projectPercentSpread.iosPercent
                ?: 0.0) / 100.0 * 4.0
        val testWeeks =
            estimatedProject.fullHumanMonth * (projectPercentSpread.testPercent
                ?: 0.0) / 100.0 * 4.0
        val manageWeeks =
            estimatedProject.fullHumanMonth * (projectPercentSpread.managePercent
                ?: 0.0) / 100.0 * 4.0


        var maxTime = 0L

        val rowList = buildList {

            val managePair = getRowForSpecialization(
                EmployeeSpheres.PROJECT_MANAGER,
                employees,
                manageWeeks
            )

            if (managePair.second > maxTime) {
                maxTime = managePair.second
            }

            add(managePair.first)

            val analyticPair = getRowForSpecialization(
                EmployeeSpheres.ANALYTIC,
                employees,
                analyticsWeeks
            )

            if (analyticPair.second > maxTime) {
                maxTime = analyticPair.second
            }

            add(analyticPair.first)


            val androidPair = getRowForSpecialization(
                EmployeeSpheres.ANDROID_DEVELOPER,
                employees,
                androidWeeks
            )

            if (androidPair.second > maxTime) {
                maxTime = androidPair.second
            }

            add(androidPair.first)


            val iosPair = getRowForSpecialization(
                EmployeeSpheres.IOS_DEVELOPER,
                employees,
                iosWeeks
            )

            if (iosPair.second > maxTime) {
                maxTime = iosPair.second
            }

            add(iosPair.first)


            val webPair = getRowForSpecialization(
                EmployeeSpheres.WEB_DEVELOPER,
                employees,
                frontendWeeks
            )

            if (webPair.second > maxTime) {
                maxTime = webPair.second
            }

            add(webPair.first)


            val backendPair = getRowForSpecialization(
                EmployeeSpheres.BACKEND_DEVELOPER,
                employees,
                backendWeeks
            )

            if (backendPair.second > maxTime) {
                maxTime = backendPair.second
            }

            add(backendPair.first)


            val testPair = getRowForSpecialization(
                EmployeeSpheres.TESTER,
                employees,
                testWeeks
            )

            if (testPair.second > maxTime) {
                maxTime = testPair.second
            }

            add(testPair.first)
        }

        val startBusiesTime = ExcelUtils.getNextMonday().time


        return TimeDiagramDto(
            rows = rowList,
            projectName = estimatedProject.projectName,
            startWeek = Date(startBusiesTime),
            lastWeek = Date(maxTime),
        )
    }


    private suspend fun getRowForSpecialization(
        sphere: EmployeeSpheres,
        employees: List<Employee>,
        weeksCount: Double,
    ): Pair<RowDto, Long> {
        var changedWeeksCount = weeksCount


        var currentDateForCount = ExcelUtils.getNextMonday().time
        val startBusiesTime = currentDateForCount

        val newEmployeesManager = employees.filter {
            it.isSpecificWorker(sphere)
        }

        val weekCounts = mutableListOf<String>()

        if (newEmployeesManager.isNotEmpty()) {
            while (changedWeeksCount > 0) {
                val weekCount =
                    ExcelUtils.getCountEmployeeFreeInWeek(
                        currentDateForCount,
                        newEmployeesManager,
                        sphere,
                    )
                changedWeeksCount -= weekCount
                weekCounts.add(weekCount.toString())
                currentDateForCount += (ONE_DAY * 7)
            }

            val newManagerEmployees =
                markBusiesForEmployees(
                    startBusiesTime,
                    currentDateForCount,
                    newEmployeesManager
                )
            uploadNewEmployees(newManagerEmployees)

        }

        val cells = weekCounts.map {
            CellDto(
                text = it,
                color = ""
            )
        }

        val firstCellString = when (sphere) {
            EmployeeSpheres.ANALYTIC -> {
                applicationContext.getString(R.string.analyticPercent)
            }
            EmployeeSpheres.PROJECT_MANAGER -> {
                applicationContext.getString(R.string.managePercent)
            }
            EmployeeSpheres.ANDROID_DEVELOPER -> {
                applicationContext.getString(R.string.androidPercent)
            }
            EmployeeSpheres.IOS_DEVELOPER -> {
                applicationContext.getString(R.string.iosPercent)
            }
            EmployeeSpheres.WEB_DEVELOPER -> {
                applicationContext.getString(R.string.frontendPercent)
            }
            EmployeeSpheres.BACKEND_DEVELOPER -> {
                applicationContext.getString(R.string.backendPercent)
            }
            EmployeeSpheres.TESTER -> {
                applicationContext.getString(R.string.testPercent)
            }
        }

        return RowDto(
            firstCellString = firstCellString,
            cells = cells,
        ) to currentDateForCount
    }

    private fun markBusiesForEmployees(
        startDate: Long,
        endDate: Long,
        employees: List<Employee>
    ): List<Employee> {
        val newEmployees = mutableListOf<Employee>()
        employees.forEach {
            val newBusies = it.busies.toMutableList()
            newBusies.add(EmployeeBusiness(startDate, endDate))
            newEmployees.add(
                Employee(
                    guid = it.guid,
                    name = it.name,
                    surname = it.surname,
                    specializations = it.specializations,
                    busies = newBusies
                )
            )
        }

        return newEmployees
    }
}