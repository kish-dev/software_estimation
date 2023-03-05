package com.example.softwareestimation.data.db.employees

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEmployee(employee: Employee)

    @Query("SELECT * FROM employees WHERE name like :name or surname like name order by name, surname")
    suspend fun getAllEmployeesByName(name: String): List<Employee>

    @Query("SELECT * FROM employees order by name, surname")
    suspend fun getAllEmployees(): List<Employee>
}