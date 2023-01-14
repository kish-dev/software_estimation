package com.example.softwareestimation.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface EstimatedProjectDao {

    @Query("SELECT * FROM estimatedProjects WHERE projectName like :projectName")
    suspend fun getEstimatedProjectByName(projectName: String): EstimatedProject

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEstimatedProject(estimatedProject: EstimatedProject)
}