package com.example.softwareestimation.data.db.estimated_project

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.softwareestimation.data.db.ProjectTypes
import com.example.softwareestimation.time_diagram_feature.CellDto
import com.example.softwareestimation.time_diagram_feature.RowDto
import com.example.softwareestimation.time_diagram_feature.TimeDiagramDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(
    tableName = "estimatedProjects",
)
data class EstimatedProject(
    @PrimaryKey
    val guid: String,
    val projectName: String,
    val projectDescription: String,
    val projectTypes: ProjectTypes,
    val fullHumanMonth: Double,
    val generatedTimeDiagramDto: TimeDiagramDto?,
)

class JSONConverterProjectTypes {
    @TypeConverter
    fun fromProjectTypes(projectTypes: ProjectTypes?): String {
        val gson = Gson()
        val type: Type = object : TypeToken<ProjectTypes?>() {}.type
        return gson.toJson(projectTypes, type)
    }

    @TypeConverter
    fun toProjectTypes(projectTypes: String?): ProjectTypes? {
        val gson = Gson()
        val type: Type = object : TypeToken<ProjectTypes?>() {}.type
        return gson.fromJson(projectTypes, type)
    }
}

class JSONConverterTimeDiagram {
    @TypeConverter
    fun fromTimeDiagram(timeDiagramDto: TimeDiagramDto?): String {
        val gson = Gson()
        val type: Type = object : TypeToken<TimeDiagramDto?>() {}.type
        return gson.toJson(timeDiagramDto, type)
    }

    @TypeConverter
    fun toTimeDiagram(timeDiagramDto: String?): TimeDiagramDto? {
        val gson = Gson()
        val type: Type = object : TypeToken<TimeDiagramDto?>() {}.type
        return gson.fromJson(timeDiagramDto, type)
    }
}

class JSONConverterRow {
    @TypeConverter
    fun fromRow(row: RowDto?): String {
        val gson = Gson()
        val type: Type = object : TypeToken<RowDto?>() {}.type
        return gson.toJson(row, type)
    }

    @TypeConverter
    fun toRow(row: String?): RowDto? {
        val gson = Gson()
        val type: Type = object : TypeToken<RowDto?>() {}.type
        return gson.fromJson(row, type)
    }
}

class JSONConverterCell {
    @TypeConverter
    fun fromRow(cell: CellDto?): String {
        val gson = Gson()
        val type: Type = object : TypeToken<CellDto?>() {}.type
        return gson.toJson(cell, type)
    }

    @TypeConverter
    fun toRow(cell: String?): CellDto? {
        val gson = Gson()
        val type: Type = object : TypeToken<CellDto?>() {}.type
        return gson.fromJson(cell, type)
    }
}