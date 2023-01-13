package com.example.softwareestimation.data

import com.example.softwareestimation.R
import com.example.softwareestimation.fill_project_form_feature.lists.enter.EnterParameterVo
import com.example.softwareestimation.fill_project_form_feature.lists.main.MainCharacteristicsVo
import java.util.*

data class FunctionPoints(
    val guid: String,
    val projectType: ProjectTypes,
    val enterParams: List<EnterParameterVo>,
    val mainSystemCharacteristics: List<MainCharacteristicsVo>
) {
    companion object {
        fun baseState(): FunctionPoints {
            return FunctionPoints(
                guid = UUID.randomUUID().toString(),
                projectType = ProjectTypes.ANDROID,
                enterParams = listOf(
                    EnterParameterVo(
                        "EI",
                        0,
                        0,
                        0,
                    ),
                    EnterParameterVo(
                        "EO",
                        0,
                        0,
                        0,
                    ),
                    EnterParameterVo(
                        "EQ",
                        0,
                        0,
                        0,
                    ),
                    EnterParameterVo(
                        "ILF",
                        0,
                        0,
                        0,
                    ),
                    EnterParameterVo(
                        "ELF",
                        0,
                        0,
                        0,
                    ),
                ),
                mainSystemCharacteristics = listOf(
                    MainCharacteristicsVo("Обмен данными:", 0),
                    MainCharacteristicsVo("Распр. функции:", 0),
                    MainCharacteristicsVo("Производительность:", 0),
                    MainCharacteristicsVo("Интенсивно используемая конфигурация:", 0),
                    MainCharacteristicsVo("Интенсивность транзакций:", 0),
                    MainCharacteristicsVo("Диалоговый ввод данных:", 0),
                    MainCharacteristicsVo("Эффективность для конечного пользователя:", 0),
                    MainCharacteristicsVo("Оперативное обновление:", 0),
                    MainCharacteristicsVo("Сложность обработки данных:", 0),
                    MainCharacteristicsVo("Повторное использование:", 0),
                    MainCharacteristicsVo("Легкость в установке:", 0),
                    MainCharacteristicsVo("Простота использования:", 0),
                    MainCharacteristicsVo("Распространенность:", 0),
                    MainCharacteristicsVo("Легкость изменения:", 0),
                )
            )
        }
    }
}

enum class ProjectTypes {
    ANDROID,
    IOS,
    WEB,
    BACKEND,
}

data class EnterParams(
    val eiEnterParam: EnterParam,
    val eoEnterParam: EnterParam,
    val eqEnterParam: EnterParam,
    val ilfEnterParam: EnterParam,
    val elfEnterParam: EnterParam,
)

data class EnterParam(
    val easyCount: Int,
    val mediumCount: Int,
    val difficultCount: Int,
)

data class MainSystemCharacteristics(
    val dataExchangeCount: Int,
    val distributionFeaturesCount: Int,
    val performanceCount: Int,
    val heavilyUsedConfigurationCount: Int,
    val transactionIntensityCount: Int,
    val dialogueInputCount: Int,
    val efficiencyForTheEndUserCount: Int,
    val liveUpdateCount: Int,
    val complexityOfDataProcessing: Int,
    val reuseCount: Int,
    val easeOfInstallationCount: Int,
    val easeOfUseCount: Int,
    val prevalenceCount: Int,
    val easeOfChangeCount: Int,
)