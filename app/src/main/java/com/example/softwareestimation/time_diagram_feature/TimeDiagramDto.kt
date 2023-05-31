package com.example.softwareestimation.time_diagram_feature

import java.util.*

data class CellDto(
    val text: String,
    val color: String,
)

data class RowDto(
    val cells: List<CellDto>
)

data class TimeDiagramDto(
    val rows: List<RowDto>,
    val projectName: String,
    val startWeek: Date,
    val lastWeek: Date,
)