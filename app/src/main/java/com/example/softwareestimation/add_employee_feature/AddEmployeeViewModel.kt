package com.example.softwareestimation.add_employee_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softwareestimation.data.db.employees.EmployeeSpecialization
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

    private val _specializations: MutableStateFlow<EmployeeSpecialization?> =
        MutableStateFlow(null)

    var specializations: StateFlow<EmployeeSpecialization?> = _specializations

    fun addEmployee(employeeVo: EmployeeVo, lambda: () -> Unit) {
        viewModelScope.launch {
            addEmployeeUseCase.addEmployee(employeeVo.toDomain())
            lambda.invoke()
        }
    }

    fun addSpecialization(specialization: EmployeeSpecialization) {
        viewModelScope.launch {
            _specializations.emit(specialization)
            val specs = _employee.value.specializations.toMutableList()
            if(!specs.contains(specialization)) {
                specs.add(specialization)
                _employee.emit(
                    EmployeeVo(
                        name = _employee.value.name,
                        surname = _employee.value.surname,
                        specializations = specs
                    )
                )
            }
        }
    }

    fun deleteEmployeeSpec(position: Int) {
        viewModelScope.launch {
            val newSpecs = _employee
                .value.specializations.toMutableList()
            newSpecs.removeAt(position)

            _employee.emit(
                EmployeeVo(
                    name = _employee.value.name,
                    surname = _employee.value.surname,
                    specializations = newSpecs
                )
            )
        }
    }
}