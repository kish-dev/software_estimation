package com.example.softwareestimation.fill_project_form_feature

import com.example.softwareestimation.R
import com.example.softwareestimation.data.db.FillFormFunctionPoints
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.fill_project_form_feature.lists.enter.EnterParameterVo
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParameterCellType
import com.example.softwareestimation.fill_project_form_feature.lists.enter.enter_param_cell.EnterParameterCellVo
import com.example.softwareestimation.fill_project_form_feature.lists.enter.toDomain
import com.example.softwareestimation.fill_project_form_feature.lists.main.MainCharacteristicsVo
import com.example.softwareestimation.fill_project_form_feature.lists.main.toDomain
import java.util.*

data class FillFormFunctionPointsVo(
    val projectName: String,
    val projectDescription: String,
    val projectType: ProjectTypes,
    val enterParams: List<EnterParameterVo>,
    val mainSystemCharacteristics: List<MainCharacteristicsVo>
) {
    companion object {
        fun baseState(): FillFormFunctionPointsVo {
            return FillFormFunctionPointsVo(
                projectName = "",
                projectDescription = "",
                projectType = ProjectTypes.ANDROID,
                enterParams = listOf(
                    EnterParameterVo(
                        R.string.ei,
                        listOf(
                            EnterParameterCellVo(
                                title = R.string.easy,
                                count = 0,
                                type = EnterParameterCellType.EASY
                            ),
                            EnterParameterCellVo(
                                title = R.string.medium,
                                count = 0,
                                type = EnterParameterCellType.MEDIUM
                            ),
                            EnterParameterCellVo(
                                title = R.string.difficult,
                                count = 0,
                                type = EnterParameterCellType.DIFFICULT
                            )
                        )
                    ),
                    EnterParameterVo(
                        R.string.eo,
                        listOf(
                            EnterParameterCellVo(
                                title = R.string.easy,
                                count = 0,
                                type = EnterParameterCellType.EASY
                            ),
                            EnterParameterCellVo(
                                title = R.string.medium,
                                count = 0,
                                type = EnterParameterCellType.MEDIUM
                            ),
                            EnterParameterCellVo(
                                title = R.string.difficult,
                                count = 0,
                                type = EnterParameterCellType.DIFFICULT
                            )
                        )
                    ),
                    EnterParameterVo(
                        R.string.eq,
                        listOf(
                            EnterParameterCellVo(
                                title = R.string.easy,
                                count = 0,
                                type = EnterParameterCellType.EASY
                            ),
                            EnterParameterCellVo(
                                title = R.string.medium,
                                count = 0,
                                type = EnterParameterCellType.MEDIUM
                            ),
                            EnterParameterCellVo(
                                title = R.string.difficult,
                                count = 0,
                                type = EnterParameterCellType.DIFFICULT
                            )
                        )
                    ),
                    EnterParameterVo(
                        R.string.ilf,
                        listOf(
                            EnterParameterCellVo(
                                title = R.string.easy,
                                count = 0,
                                type = EnterParameterCellType.EASY
                            ),
                            EnterParameterCellVo(
                                title = R.string.medium,
                                count = 0,
                                type = EnterParameterCellType.MEDIUM
                            ),
                            EnterParameterCellVo(
                                title = R.string.difficult,
                                count = 0,
                                type = EnterParameterCellType.DIFFICULT
                            )
                        )
                    ),
                    EnterParameterVo(
                        R.string.elf,
                        listOf(
                            EnterParameterCellVo(
                                title = R.string.easy,
                                count = 0,
                                type = EnterParameterCellType.EASY
                            ),
                            EnterParameterCellVo(
                                title = R.string.medium,
                                count = 0,
                                type = EnterParameterCellType.MEDIUM
                            ),
                            EnterParameterCellVo(
                                title = R.string.difficult,
                                count = 0,
                                type = EnterParameterCellType.DIFFICULT
                            )
                        )
                    ),
                ),
                mainSystemCharacteristics = listOf(
                    MainCharacteristicsVo(R.string.data_exchange, 0),
                    MainCharacteristicsVo(R.string.distributed_functions, 0),
                    MainCharacteristicsVo(R.string.capacity, 0),
                    MainCharacteristicsVo(R.string.heavily_used_configuration, 0),
                    MainCharacteristicsVo(R.string.transaction_intensity, 0),
                    MainCharacteristicsVo(R.string.dialogue_input, 0),
                    MainCharacteristicsVo(R.string.end_user_efficiency, 0),
                    MainCharacteristicsVo(R.string.live_updates, 0),
                    MainCharacteristicsVo(R.string.complexity_of_data_processing, 0),
                    MainCharacteristicsVo(R.string.reuse, 0),
                    MainCharacteristicsVo(R.string.ease_of_installation, 0),
                    MainCharacteristicsVo(R.string.ease_of_use, 0),
                    MainCharacteristicsVo(R.string.prevalence, 0),
                    MainCharacteristicsVo(R.string.ease_of_change, 0),
                )
            )
        }
    }
}

fun FillFormFunctionPointsVo.toDomain(): FillFormFunctionPoints {
    return FillFormFunctionPoints(
        guid = UUID.randomUUID().toString(),
        projectName = this.projectName,
        projectDescription = this.projectDescription,
        projectType = this.projectType,
        enterParams = this.enterParams.map {
            it.toDomain()
        },
        mainSystemCharacteristics = this.mainSystemCharacteristics.map {
            it.toDomain()
        }
    )
}