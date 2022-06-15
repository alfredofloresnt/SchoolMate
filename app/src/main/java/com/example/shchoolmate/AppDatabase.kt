package com.example.shchoolmate

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Course::class, Todo::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao() : CourseDao
    abstract fun todoDao() : TodoDao
    companion object {

        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context) :AppDatabase {
            return  INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(context, AppDatabase::class.java, "app_database").fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}