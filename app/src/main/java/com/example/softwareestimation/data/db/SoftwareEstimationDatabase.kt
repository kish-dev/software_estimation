package com.example.softwareestimation.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeDao
import com.example.softwareestimation.data.db.employees.JSONConverterSpecializations
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import com.example.softwareestimation.data.db.estimated_project.EstimatedProjectDao

@Database(
    entities = [ProjectPercentSpreadForTypes::class,
        EstimatedProject::class,
               Employee::class,],
    version = 5, exportSchema = false
)
@TypeConverters(JSONConverterSpecializations::class)
abstract class SoftwareEstimationDatabase : RoomDatabase() {

    abstract fun estimatedProjectDao(): EstimatedProjectDao

    abstract fun projectPercentSpreadDao(): ProjectPercentSpreadForTypesDao

    abstract fun employeeDao(): EmployeeDao
}