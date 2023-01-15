package com.example.softwareestimation.data

import com.example.softwareestimation.data.db.EstimatedProject
import com.example.softwareestimation.data.db.EstimatedProjectDao
import com.example.softwareestimation.data.db.ProjectTypes
import javax.inject.Inject

class FillFormRepository @Inject constructor(
    private val estimatedProjectDao: EstimatedProjectDao
) {

    fun getProjectTypeLOC(type: ProjectTypes): Int {
        return when (type) {
            ProjectTypes.ANDROID -> {
                ANDROID_FP_TO_LOC
            }
            ProjectTypes.IOS -> {
                IOS_FP_TO_LOC
            }
            ProjectTypes.BACKEND -> {
                BACKEND_FP_TO_LOC
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
        val ANDROID_FP_TO_LOC = 53
        val IOS_FP_TO_LOC = 45
        val BACKEND_FP_TO_LOC = 53
        val WEB_FP_TO_LOC = 47
    }
}