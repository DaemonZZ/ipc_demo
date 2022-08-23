package com.thangdn6.aidlserver.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thangdn6.aidlserver.database.dao.StudentDAO
import com.thangdn6.aidlserver.model.Student

@Database(entities = [Student::class],version = 1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun getDao(): StudentDAO
}