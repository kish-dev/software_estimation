package com.example.softwareestimation.all_projects_feature

import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import javax.inject.Inject

class AllProjectsInteractor @Inject constructor(
    private val repository: AllProjectsRepository,
): AllProjectsUseCase {

    override suspend fun getAllProjects(searchText: String): List<EstimatedProject> =
        repository.getAllProject("%${searchText}%")

    override suspend fun getAllProjects(): List<EstimatedProject> =
        repository.getAllProject()
}