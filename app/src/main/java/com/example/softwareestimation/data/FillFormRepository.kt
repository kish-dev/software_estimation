package com.example.softwareestimation.data

import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import com.example.softwareestimation.data.db.estimated_project.EstimatedProjectDao
import com.example.softwareestimation.data.db.ProjectTypes
import javax.inject.Inject

class FillFormRepository @Inject constructor(
    private val estimatedProjectDao: EstimatedProjectDao
) {

    fun getProjectTypeLOC(type: ProjectTypes): Int {
        return when (type) {
            ProjectTypes.MOBILE -> {
                MOBILE_FP_TO_LOC
            }
            ProjectTypes.WEB -> {
                WEB_FP_TO_LOC
            }
        }
    }

    suspend fun saveEstimatedProjectResult(estimatedProject: EstimatedProject) {
        return estimatedProjectDao.addEstimatedProject(estimatedProject)
    }

    private companion object {
        const val MOBILE_FP_TO_LOC = 50
        const val WEB_FP_TO_LOC = 47
    }
}