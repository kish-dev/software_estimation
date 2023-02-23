package com.example.softwareestimation.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "projectPercentSpread",
    indices = [Index(
        value = arrayOf("type"),
        unique = true
    )]
)
data class ProjectPercentSpreadForTypes(
    @PrimaryKey
    val guid: String,
    val type: ProjectTypes,
    val analyticPercent: Double?,
    val androidPercent: Double?,
    val frontendPercent: Double?,
    val backendPercent: Double?,
    val iosPercent: Double?,
    val testPercent: Double?,
    val managePercent: Double?,
) {
    companion object {
        val typeInformation = listOf<ProjectPercentSpreadForTypes>(
            ProjectPercentSpreadForTypes(
                guid = UUID.randomUUID().toString(),
                type = ProjectTypes.WEB,
                analyticPercent = 7.2,
                androidPercent = null,
                frontendPercent = 24.6,
                backendPercent = 30.0,
                iosPercent = null,
                testPercent = 15.7,
                managePercent = 22.5
            ),
            ProjectPercentSpreadForTypes(
                guid = UUID.randomUUID().toString(),
                type = ProjectTypes.MOBILE,
                analyticPercent = 3.8,
                androidPercent = 25.8,
                frontendPercent = null,
                backendPercent = 18.9,
                iosPercent = 16.1,
                testPercent = 25.1,
                managePercent = 10.3
            )
        )
    }
}


