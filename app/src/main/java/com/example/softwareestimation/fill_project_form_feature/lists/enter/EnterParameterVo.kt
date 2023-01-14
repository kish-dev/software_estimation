package com.example.softwareestimation.fill_project_form_feature.lists.enter

import com.example.softwareestimation.data.db.EnterParameter
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParameterCellType
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParameterCellVo

data class EnterParameterVo(
    val title: Int,
    val listOfParams: List<EnterParameterCellVo>,
)

fun EnterParameterVo.toDomain(): EnterParameter {
    return EnterParameter(
        easyCount = this.listOfParams.firstOrNull { it.type == EnterParameterCellType.EASY }?.count
            ?: 0,
        mediumCount = this.listOfParams.firstOrNull {
            it.type == EnterParameterCellType.MEDIUM
        }?.count ?: 0,
        difficultCount = this.listOfParams.firstOrNull {
            it.type == EnterParameterCellType.DIFFICULT
        }?.count ?: 0,
    )
}