package com.example.softwareestimation.employee_details_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softwareestimation.data.db.employees.Employee
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmployeeDetailsEditViewModel @Inject constructor(
    private val useCase: EmployeeDetailsEditInteractor
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