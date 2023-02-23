package com.example.softwareestimation.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProjectPercentSpreadForTypesDao {

    @Query("SELECT * FROM projectPercentSpread WHERE type like :type")
    suspend fun getProjectPercentByType(type: ProjectTypes): ProjectPercentSpreadForTypes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProjectPercentSpread(projectPercentSpreadForTypes: ProjectPercentSpreadForTypes)
}