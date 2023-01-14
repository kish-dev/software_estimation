package com.example.softwareestimation.data

import com.example.softwareestimation.data.db.EstimatedProject
import com.example.softwareestimation.data.db.EstimatedProjectDao
import com.example.softwareestimation.data.db.ProjectTypes
import javax.inject.Inject

class FillFormRepository @Inject constructor(
    private val estimatedProjectDao: EstimatedProjectDao
){

    fun getProjectTypeLOC(type: ProjectTypes): Int {
        return 53
    }

    suspend fun saveEstimatedProjectResult(estimatedProject: EstimatedProject) {
        return estimatedProjectDao.addEstimatedProject(estimatedProject)
    }
}