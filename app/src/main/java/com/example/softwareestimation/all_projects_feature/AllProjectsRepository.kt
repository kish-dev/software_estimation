package com.example.softwareestimation.all_projects_feature

import android.util.Log
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import com.example.softwareestimation.data.db.estimated_project.EstimatedProjectDao
import javax.inject.Inject

class AllProjectsRepository @Inject constructor(private val estimatedProjectDao: EstimatedProjectDao) {

    suspend fun getAllProject(searchText: String): List<EstimatedProject> {
        val estimatedProjects = estimatedProjectDao.getAllProjectsByName(searchText)
        Log.d("AllProjectsRepository", "getAllProjectByName: ${estimatedProjects.size}")
        return estimatedProjects
    }

    suspend fun getAllProject(): List<EstimatedProject> {
        val estimatedProjects = estimatedProjectDao.getAllProjects()
        Log.d("AllProjectsRepository", "getAllProject: ${estimatedProjects.size}")
        return estimatedProjects
    }


}