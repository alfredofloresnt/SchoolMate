package com.example.shchoolmate

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Course::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao() : CourseDao
    companion object {

        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context) :AppDatabase {
            return  INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}