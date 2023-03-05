package com.example.softwareestimation.data.db.estimated_project

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EstimatedProjectDao {

    @Query("SELECT * FROM estimatedProjects WHERE projectName = :projectName LIMIT 1")
    suspend fun getEstimatedProjectByName(projectName: String): EstimatedProject

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEstimatedProject(estimatedProject: EstimatedProject)

    @Query("SELECT * FROM estimatedProjects WHERE projectName like :searchText order by projectName")
    suspend fun getAllProjectsByName(searchText: String): List<EstimatedProject>

    @Query("SELECT * FROM estimatedProjects order by projectName")
    suspend fun getAllProjects(): List<EstimatedProject>
}