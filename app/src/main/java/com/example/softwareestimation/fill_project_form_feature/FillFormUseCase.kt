package com.example.softwareestimation.fill_project_form_feature

interface FillFormUseCase {
    fun checkFormFullFilledCorrectly(): Boolean
    fun sendFilledForm()
}