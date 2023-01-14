package com.example.softwareestimation.fill_project_form_feature

import com.example.softwareestimation.data.FillFormRepository
import com.example.softwareestimation.data.db.EstimatedProject
import com.example.softwareestimation.data.db.FillFormFunctionPoints
import com.example.softwareestimation.data.db.ProjectTypes
import java.util.*
import javax.inject.Inject
import kotlin.math.pow

class FillFormInteractor @Inject constructor(
    private val repository: FillFormRepository,
): FillFormUseCase {

    override suspend fun sendFilledForm(form: FillFormFunctionPoints) {
        repository.saveEstimatedProjectResult(estimateProject(form))
    }

    private fun estimateProject(form: FillFormFunctionPoints): EstimatedProject {

        //данные идеально правильные
        var functionalSize = 0
        functionalSize += form.enterParams[0].easyCount * 3
        functionalSize += form.enterParams[0].mediumCount * 4
        functionalSize += form.enterParams[0].difficultCount * 6

        functionalSize += form.enterParams[1].easyCount * 4
        functionalSize += form.enterParams[1].mediumCount * 5
        functionalSize += form.enterParams[1].difficultCount * 7

        functionalSize += form.enterParams[2].easyCount * 3
        functionalSize += form.enterParams[2].mediumCount * 4
        functionalSize += form.enterParams[2].difficultCount * 6

        functionalSize += form.enterParams[3].easyCount * 7
        functionalSize += form.enterParams[3].mediumCount * 10
        functionalSize += form.enterParams[3].difficultCount * 15

        functionalSize += form.enterParams[4].easyCount * 5
        functionalSize += form.enterParams[4].mediumCount * 7
        functionalSize += form.enterParams[4].difficultCount * 10

        var mainCharacteristicsSize = 0
        form.mainSystemCharacteristics.forEach {
            mainCharacteristicsSize += it.count
        }

        val correctedFunctionalSize: Long = (
                functionalSize * (0.65 + 0.01 * mainCharacteristicsSize)).toLong()
        val loc: Long = correctedFunctionalSize * getProjectTypeLOC(form.projectType)

        val humanMonth = getHumanMonthFromLOC(loc)

        return EstimatedProject(
            guid = UUID.randomUUID().toString(),
            projectName = form.projectName,
            projectDescription = form.projectDescription,
            projectTypes = form.projectType,
            fullHumanMonth = humanMonth
        )
    }

    private fun getProjectTypeLOC(type: ProjectTypes): Int {
        return repository.getProjectTypeLOC(type)
    }

    private fun getHumanMonthFromLOC(loc: Long): Double {
        return A * (loc.toKLOC().pow(B))
    }

    private fun Long.toKLOC() : Double =
        this.toDouble()/1000

    companion object {
        private val A = 2.4
        private val B = 1.05
    }
}