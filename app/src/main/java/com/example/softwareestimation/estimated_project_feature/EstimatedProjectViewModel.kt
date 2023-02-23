package com.example.softwareestimation.estimated_project_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softwareestimation.data.db.EstimatedProject
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypes
import com.example.softwareestimation.data.db.ProjectTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstimatedProjectViewModel @Inject constructor(
    private val useCase: EstimatedProjectUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    private val _estimatedProject: MutableStateFlow<EstimatedProject> =
        MutableStateFlow(
            EstimatedProject(
                "",
                "",
                "",
                ProjectTypes.MOBILE,
                0.0,
            )
        )

    var estimatedProject: StateFlow<EstimatedProject> =
        _estimatedProject

    private val _projectPercentSpread: MutableStateFlow<ProjectPercentSpreadForTypes?> =
        MutableStateFlow(null)

    var projectPercentSpread: StateFlow<ProjectPercentSpreadForTypes?> =
        _projectPercentSpread

    fun updateEstimatedProject(projectName: String) {
        viewModelScope.launch(handler) {
            val estimatedProject = useCase.getEstimatedProject(projectName)
            _estimatedProject.emit(estimatedProject)
            updateProjectPercentSpread(estimatedProject.projectTypes)
        }
    }

    fun updateProjectPercentSpread(type: ProjectTypes) {
        viewModelScope.launch(handler) {
            val projectPercentSpread = useCase.getProjectPercentSpread(type)
            _projectPercentSpread.emit(projectPercentSpread)
        }
    }

}