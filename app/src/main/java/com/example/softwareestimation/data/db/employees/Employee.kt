package com.example.softwareestimation.data.db.employees

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey
    val guid: String,
    val name: String,
    val surname: String,
    val specializations: List<EmployeeSpecialization>,
    //TODO Add date of projects
)

class JSONConverterSpecializations {
    @TypeConverter
    fun fromSpecializations(specializations: List<EmployeeSpecialization>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<EmployeeSpecialization>?>() {}.type
        return gson.toJson(specializations, type)
    }

    @TypeConverter
    fun toSpecializations(specializations: String?): List<EmployeeSpecialization>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<EmployeeSpecialization>?>() {}.type
        return gson.fromJson(specializations, type)
    }
}
