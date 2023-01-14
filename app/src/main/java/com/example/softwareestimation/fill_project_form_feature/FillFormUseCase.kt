package com.example.softwareestimation.fill_project_form_feature

import com.example.softwareestimation.data.db.FillFormFunctionPoints

interface FillFormUseCase {
    suspend fun sendFilledForm(form: FillFormFunctionPoints)
}