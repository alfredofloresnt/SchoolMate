package com.example.shchoolmate

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CoursesViewModel(private val courseDao: CourseDao): ViewModel() {
    private val _courses = mutableListOf<Course>()

    suspend fun getAllCourses() = courseDao.getAllCourses()

    fun getCourse(id: Int): Course = courseDao.getCourse(id)

    suspend fun insertCourse(course: Course) = courseDao.insertCourse(course)

    fun deleteCourse(course: Course) = courseDao.deleteCourse(course)

    fun updateCourse(course: Course) = courseDao.updateCourse(course)

    var _activeDate: Calendar = Calendar.getInstance()
    var _activeWeek: Int = _activeDate.get(Calendar.WEEK_OF_YEAR)
    val _today : Calendar = Calendar.getInstance()

    val activeDate get() = _activeDate
    val activeWeek get() = _activeWeek

    fun prevWeek(){
        _activeWeek--
    }

    fun nextWeek(){
       _activeWeek++
    }

    fun goToToday(){
        _activeDate = _today
    }

}

class CoursesViewModelFactory (private val courseDao: CourseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoursesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoursesViewModel(courseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}