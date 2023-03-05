package com.example.softwareestimation.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.softwareestimation.data.db.estimated_project.EstimatedProject
import com.example.softwareestimation.data.db.estimated_project.EstimatedProjectDao

@Database(
    entities = [ProjectPercentSpreadForTypes::class,
        EstimatedProject::class], version = 4, exportSchema = false
)
abstract class SoftwareEstimationDatabase : RoomDatabase() {

    abstract fun estimatedProjectDao(): EstimatedProjectDao

    abstract fun projectPercentSpreadDao(): ProjectPercentSpreadForTypesDao
}