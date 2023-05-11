package com.example.softwareestimation.employee_details_feature

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
class EmployeeDetailsViewModel @Inject constructor(
    private val useCase: EmployeeDetailsInteractor
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    private val _employee: MutableStateFlow<Employee> =
        MutableStateFlow(
            Employee(
                "",
                "",
                "",
                emptyList(),
                emptyList()
            )
        )

    var employee: StateFlow<Employee> =
        _employee

    fun getEmployee(guid: String) {
        viewModelScope.launch(handler) {
            val employee = useCase.getEmployee(guid) ?: return@launch

            _employee.emit(employee)
        }
    }
}