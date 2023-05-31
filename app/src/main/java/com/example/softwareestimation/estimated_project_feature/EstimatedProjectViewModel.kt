package com.example.softwareestimation.estimated_project_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import com.example.softwareestimation.data.db.ProjectPercentSpreadForTypes
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.data.db.employees.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEmpty
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

    private val _employees: MutableStateFlow<List<Employee>> =
        MutableStateFlow(emptyList())

    var employees: StateFlow<List<Employee>> =
        _employees

    fun updateEstimatedProject(projectName: String) {
        viewModelScope.launch(handler) {
            val estimatedProject = useCase.getEstimatedProject(projectName)
            _estimatedProject.emit(estimatedProject)
            updateProjectPercentSpread(estimatedProject.projectTypes)
        }
    }

    fun getEmployees() {
        viewModelScope.launch(handler) {
            val employees = useCase.getEmployees()
            _employees.emit(employees)
        }
    }

    private fun updateProjectPercentSpread(type: ProjectTypes) {
        viewModelScope.launch(handler) {
            val projectPercentSpread = useCase.getProjectPercentSpread(type)
            _projectPercentSpread.emit(projectPercentSpread)
        }
    }

    fun uploadNewEmployees(employees: List<Employee>) {
        viewModelScope.launch(handler) {
            useCase.uploadNewEmployees(employees)
            getEmployees()
        }
    }

}