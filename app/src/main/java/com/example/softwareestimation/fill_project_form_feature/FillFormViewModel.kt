package com.example.softwareestimation.fill_project_form_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FillFormViewModel @Inject constructor(
    private val useCase: FillFormUseCase,
) : ViewModel() {

    private val _functionPoint: MutableStateFlow<FillFormFunctionPointsVo> =
        MutableStateFlow(FillFormFunctionPointsVo.baseState())
    var functionPoint: StateFlow<FillFormFunctionPointsVo> =
        _functionPoint

    fun fillForm(fillForm: FillFormFunctionPointsVo) {
        viewModelScope.launch {
            useCase.sendFilledForm(fillForm.toDomain())
        }
    }

    fun updateViewModelState(fillForm: FillFormFunctionPointsVo) {
        viewModelScope.launch {
            _functionPoint.emit(fillForm)
        }
    }
}