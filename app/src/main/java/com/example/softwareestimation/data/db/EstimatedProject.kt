package com.example.softwareestimation.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(
    tableName = "estimatedProjects",
    indices = [Index(
        value = arrayOf("projectName"),
        unique = true
    )]
)
data class EstimatedProject(
    @PrimaryKey
    val guid: String,
    val projectName: String,
    val projectDescription: String,
    val projectTypes: ProjectTypes,
    val fullHumanMonth: Double,
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