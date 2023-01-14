package com.example.softwareestimation.estimated_project_feature

import com.example.softwareestimation.data.db.EstimatedProject

interface EstimatedProjectUseCase {
   suspend fun getEstimatedProject(projectName: String): EstimatedProject
}