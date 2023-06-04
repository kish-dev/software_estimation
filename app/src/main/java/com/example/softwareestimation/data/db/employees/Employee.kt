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
    val busies: List<EmployeeBusiness>,
) {
    fun isSpecificWorker(sphere: EmployeeSpheres): Boolean {
        return specializations.contains(
            EmployeeSpecialization(sphere, EmployeesLevels.INTERN)
        ) ||
                specializations.contains(
                    EmployeeSpecialization(sphere, EmployeesLevels.JUNIOR)
                )
                ||
                specializations.contains(
                    EmployeeSpecialization(sphere, EmployeesLevels.MIDDLE)
                ) ||
                specializations.contains(
                    EmployeeSpecialization(sphere, EmployeesLevels.SENIOR)
                ) || specializations.contains(
            EmployeeSpecialization(sphere, EmployeesLevels.LEAD)
        )
    }

    fun hasBusies(startDate: Long, endDate: Long): Boolean {
        busies.forEach {
            if (it.startDate <= startDate && it.endDate >= endDate) {
                return true
            }
        }

        return false
    }
}

class JSONConverterBusiness {
    @TypeConverter
    fun fromBusiness(business: List<EmployeeBusiness>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<EmployeeBusiness>?>() {}.type
        return gson.toJson(business, type)
    }

    @TypeConverter
    fun toBusiness(business: String?): List<EmployeeBusiness>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<EmployeeBusiness>?>() {}.type
        return gson.fromJson(business, type)
    }
}

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
