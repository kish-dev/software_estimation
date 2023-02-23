package com.example.softwareestimation.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProjectPercentSpreadForTypes::class,
        EstimatedProject::class], version = 1, exportSchema = false
)
abstract class SoftwareEstimationDatabase : RoomDatabase() {

    abstract fun estimatedProjectDao(): EstimatedProjectDao

    abstract fun projectPercentSpreadDao(): ProjectPercentSpreadForTypesDao
}