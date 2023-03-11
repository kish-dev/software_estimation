package com.example.softwareestimation.add_employee_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEmployeeViewModel @Inject constructor(
    private val addEmployeeUseCase: AddEmployeeUseCase
) : ViewModel() {

    private val _employee: MutableStateFlow<EmployeeVo> =
        MutableStateFlow(EmployeeVo.baseState())

    var employee: StateFlow<EmployeeVo> = _employee

    fun addEmployee(employeeVo: EmployeeVo, lambda: () -> Unit) {
        viewModelScope.launch {
            addEmployeeUseCase.addEmployee(employeeVo.toDomain())
            lambda.invoke()
        }
    }

    //TODO подумать как удалять (пока что можно брать список с адаптера и удалять по индексу,
    //TODO либо сохранять все во вьюмодельку (так лучше), удалять оттуда же и обновляться

    fun updateViewModelState(employeeVo: EmployeeVo) {
        viewModelScope.launch {
            _employee.emit(employeeVo)
        }
    }

    fun deleteEmployeeSpec(position: Int) {
        viewModelScope.launch {
            val newEmployeeSpecs = _employee
                .value.specializations.toMutableList()
            newEmployeeSpecs.removeAt(position)

            _employee.emit(
                EmployeeVo(
                    name = _employee.value.name,
                    surname = _employee.value.surname,
                    specializations = newEmployeeSpecs
                )
            )
        }
    }
}