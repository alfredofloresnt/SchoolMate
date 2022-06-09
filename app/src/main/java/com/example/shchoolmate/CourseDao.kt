package com.example.shchoolmate

import androidx.room.*

@Dao
interface CourseDao {

    // Get all courses
    @Query("SELECT * FROM Course")
    suspend fun getAllCourses(): List<Course>

    // Get single course by id
    @Query("SELECT * FROM Course WHERE id = :id")
    fun getCourse(id: Int): Course

    // Insert course
    @Insert
    suspend fun insertCourse(course: Course): Long

    // Delete course
    @Delete
    fun deleteCourse(course: Course)

    // Update course
    @Update
    fun updateCourse(course: Course)
}