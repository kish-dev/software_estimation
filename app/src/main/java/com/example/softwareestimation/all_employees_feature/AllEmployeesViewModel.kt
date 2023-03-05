package com.example.softwareestimation.all_employees_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softwareestimation.data.db.employees.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllEmployeesViewModel @Inject constructor(
    private val allEmployeesUseCase: AllEmployeesUseCase
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    private val _allEmployees: MutableStateFlow<List<Employee>> =
        MutableStateFlow(emptyList())

    var allEmployee: StateFlow<List<Employee>> = _allEmployees

    fun getAllEmployees(name: String?) {
        viewModelScope.launch {
            val employees = if (name == null || name.isBlank()) {
                allEmployeesUseCase.getAllEmployees()
            } else {
                allEmployeesUseCase.getAllEmployees(name)
            }
            _allEmployees.emit(employees)
        }

    }
}