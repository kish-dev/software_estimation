package com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell

data class EnterParameterCellVo(
    val title: Int,
    var count: Int?,
    val type: EnterParameterCellType,
)

enum class EnterParameterCellType {
    EASY,
    MEDIUM,
    DIFFICULT
}