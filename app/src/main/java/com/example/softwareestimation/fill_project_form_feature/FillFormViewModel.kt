package com.example.softwareestimation.fill_project_form_feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.softwareestimation.data.FunctionPoints

class FillFormViewModel(
    private val useCase: FillFormUseCase,
) : ViewModel() {

    private val _functionPoint: MutableLiveData<FunctionPoints> =
        MutableLiveData(FunctionPoints.baseState())
}