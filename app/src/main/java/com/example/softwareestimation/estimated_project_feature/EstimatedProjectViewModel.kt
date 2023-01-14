package com.example.softwareestimation.estimated_project_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softwareestimation.data.db.EstimatedProject
import com.example.softwareestimation.data.db.ProjectTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstimatedProjectViewModel @Inject constructor(
    private val useCase: EstimatedProjectUseCase
) : ViewModel() {

    private val _estimatedProject: MutableStateFlow<EstimatedProject> =
        MutableStateFlow(
            EstimatedProject(
                "",
                "",
                "",
                ProjectTypes.ANDROID,
                0.0,
            )
        )
    var estimatedProject: StateFlow<EstimatedProject> =
        _estimatedProject

    fun getEstimatedProject(projectName: String) {
        viewModelScope.launch {
            val estimatedProject = useCase.getEstimatedProject(projectName)
            _estimatedProject.emit(estimatedProject)
        }
    }

}