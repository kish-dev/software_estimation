package com.example.softwareestimation.employee_details_edit_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeBusiness
import com.example.softwareestimation.data.db.employees.EmployeeSpecialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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
                emptyList(),
                emptyList(),
            )
        )
    var employee: StateFlow<Employee> = _employee

    private val _listSpecializations: MutableStateFlow<List<EmployeeSpecialization>> =
        MutableStateFlow(emptyList())
    var listSpecialization: StateFlow<List<EmployeeSpecialization>> = _listSpecializations


    private val _listBusiness: MutableStateFlow<List<EmployeeBusiness>> =
        MutableStateFlow(emptyList())
    var listBusiness: StateFlow<List<EmployeeBusiness>> = _listBusiness


    private val _specializations: MutableStateFlow<EmployeeSpecialization?> =
        MutableStateFlow(null)
    var specializations: StateFlow<EmployeeSpecialization?> = _specializations


    private val _busies: MutableStateFlow<EmployeeBusiness?> =
        MutableStateFlow(null)
    var busies: StateFlow<EmployeeBusiness?> = _busies


    fun getEmployee(guid: String) {
        viewModelScope.launch(handler) {
            val employee = useCase.getEmployee(guid) ?: return@launch

            _employee.emit(employee)
            _listBusiness.emit(employee.busies)
            _listSpecializations.emit(employee.specializations)
        }
    }

    fun updateEmployee(employee: Employee) {
        viewModelScope.launch(handler) {
            useCase.updateEmployee(employee)
        }
    }

    fun addSpecialization(specialization: EmployeeSpecialization) {
        viewModelScope.launch {
            _specializations.emit(specialization)
            val specs = _listSpecializations.value.toMutableList()
            if (!specs.contains(specialization)) {
                specs.add(specialization)
                _listSpecializations.emit(specs)
            }
        }
    }

    fun addBusy(business: EmployeeBusiness) {
        viewModelScope.launch {
            _busies.emit(business)
            val newBusies = _listBusiness.value.toMutableList()
            if (!newBusies.contains(business)) {
                newBusies.add(business)
                _listBusiness.emit(newBusies)
            }
        }
    }

    fun deleteEmployeeSpec(position: Int) {
        viewModelScope.launch {
            val newSpecs = listSpecialization.value.toMutableList()
            newSpecs.removeAt(position)

            _listSpecializations.emit(newSpecs)
        }
    }

    fun deleteEmployeeBusy(position: Int) {
        viewModelScope.launch {
            val newBusies = _listBusiness.value.toMutableList()
            newBusies.removeAt(position)

            _listBusiness.emit(newBusies)
        }
    }
}