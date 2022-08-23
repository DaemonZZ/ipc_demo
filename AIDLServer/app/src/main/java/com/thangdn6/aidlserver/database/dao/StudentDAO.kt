package com.thangdn6.aidlserver.database.dao

import androidx.room.*
import com.thangdn6.aidlserver.model.Student

@Dao
interface StudentDAO {
    @Query("select * from student")
    suspend fun getListStds():List<Student>

    @Insert
    suspend fun insertStd(std:Student)

    @Update
    suspend fun updateStd(std: Student)

    @Delete
    suspend fun deleteStd(std: Student)
}