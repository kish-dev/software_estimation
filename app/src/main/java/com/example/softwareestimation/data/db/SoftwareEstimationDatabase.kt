package com.example.softwareestimation.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.softwareestimation.data.db.employees.Employee
import com.example.softwareestimation.data.db.employees.EmployeeDao
import com.example.softwareestimation.data.db.employees.JSONConverterBusiness
import com.example.softwareestimation.data.db.employees.JSONConverterSpecializations
import com.example.softwareestimation.data.db.estimated_project.*

@Database(
    entities = [ProjectPercentSpreadForTypes::class,
        EstimatedProject::class,
               Employee::class,],
    version = 7,
    exportSchema = false,
)
@TypeConverters(
    JSONConverterSpecializations::class,
    JSONConverterBusiness::class,
    JSONConverterTimeDiagram::class,
    JSONConverterProjectTypes::class,
    JSONConverterRow::class,
    JSONConverterCell::class,
)
abstract class SoftwareEstimationDatabase : RoomDatabase() {

    abstract fun estimatedProjectDao(): EstimatedProjectDao

    abstract fun projectPercentSpreadDao(): ProjectPercentSpreadForTypesDao

    abstract fun employeeDao(): EmployeeDao
}