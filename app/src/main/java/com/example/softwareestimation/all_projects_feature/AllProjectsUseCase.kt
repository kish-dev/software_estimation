package com.example.softwareestimation.all_projects_feature

import com.example.softwareestimation.data.db.estimated_project.EstimatedProject

interface AllProjectsUseCase {

    suspend fun getAllProjects(searchText: String): List<EstimatedProject>
    suspend fun getAllProjects(): List<EstimatedProject>

}