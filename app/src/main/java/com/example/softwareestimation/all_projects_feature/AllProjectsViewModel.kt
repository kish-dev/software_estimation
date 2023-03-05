package com.example.softwareestimation.all_projects_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProjectsViewModel @Inject constructor(
    private val interactor: AllProjectsInteractor
) : ViewModel() {


    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    private var _allProjects: MutableStateFlow<List<EstimatedProject>> =
        MutableStateFlow(emptyList())

    var allProjects: StateFlow<List<EstimatedProject>> = _allProjects

    fun getAllProjects(searchText: String?) {
        viewModelScope.launch(handler) {
            val newAllProjects = if (searchText == null || searchText.isBlank()) {
                interactor.getAllProjects()
            } else {
                interactor.getAllProjects(searchText)
            }
            _allProjects.emit(newAllProjects)
        }
    }

}