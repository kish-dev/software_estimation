package com.example.softwareestimation.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProjectPercentSpreadForTypes(
    @PrimaryKey
    val guid: String,
    val type: ProjectTypes,
    val analyticPercent: Double,
    val androidPercent: Double,
    val frontendPercent: Double,
    val backendPercent: Double,
    val iosPercent: Double,
    val testPercent: Double,
    val managePercent: Double,
)
