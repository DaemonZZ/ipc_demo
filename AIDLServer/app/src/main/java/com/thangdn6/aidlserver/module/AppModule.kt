package com.thangdn6.aidlserver.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thangdn6.aidlserver.database.StudentDatabase
import com.thangdn6.aidlserver.database.dao.StudentDAO
import com.thangdn6.aidlserver.model.Student
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideIOScope() = CoroutineScope(Dispatchers.IO)

    @Singleton
    @Provides
    fun provideDAO(db: StudentDatabase): StudentDAO = db.getDao()

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        scope: CoroutineScope,
        daoProvider: Provider<StudentDAO>
    ): StudentDatabase {

        return Room.databaseBuilder(context, StudentDatabase::class.java, "TransDatabase")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    scope.launch {
                        fakeTransData(daoProvider.get())
                    }
                }


            })
            .fallbackToDestructiveMigration()
            .build()
    }

    private suspend fun fakeTransData(dao: StudentDAO) {
        for (i in 1..20) {
            dao.insertStd(
                Student().apply {
                    name = "Student $i"
                    age = 22
                    math = 0f
                    physic = 0f
                    chemistry = 0f
                    english = 0f
                    literature = 0f
                }
            )
        }
    }
}