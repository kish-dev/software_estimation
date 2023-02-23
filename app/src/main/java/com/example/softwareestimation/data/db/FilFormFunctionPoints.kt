package com.example.softwareestimation.data.db

data class FillFormFunctionPoints(
    val guid: String,
    val projectName: String,
    val projectDescription: String,
    val projectType: ProjectTypes,
    val enterParams: List<EnterParameter>,
    val mainSystemCharacteristics: List<MainCharacteristics>
)

enum class ProjectTypes {
    MOBILE,
    WEB,
}

data class EnterParams(
    val eiEnterParam: EnterParameter,
    val eoEnterParam: EnterParameter,
    val eqEnterParam: EnterParameter,
    val ilfEnterParam: EnterParameter,
    val elfEnterParam: EnterParameter,
)

data class EnterParameter(
    val easyCount: Int,
    val mediumCount: Int,
    val difficultCount: Int,
)

data class MainCharacteristics(
    val count: Int,
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