package com.example.softwareestimation.fill_project_form_feature.lists.main

import com.example.softwareestimation.data.db.MainCharacteristics

data class MainCharacteristicsVo(
    val title: Int,
    var count: Int = 0,
)

fun MainCharacteristicsVo.toDomain(): MainCharacteristics {
    return MainCharacteristics(count = this.count)
}