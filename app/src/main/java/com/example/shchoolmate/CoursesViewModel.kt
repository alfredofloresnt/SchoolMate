package com.example.shchoolmate

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CoursesViewModel(private val courseDao: CourseDao): ViewModel() {

    suspend fun getAllCourses() = courseDao.getAllCourses()

    fun getCourse(id: Int): Course = courseDao.getCourse(id)

    suspend fun insertCourse(course: Course) = courseDao.insertCourse(course)

    fun deleteCourse(course: Course) = courseDao.deleteCourse(course)

    fun updateCourse(course: Course) = courseDao.updateCourse(course)

    var _activeDate: Calendar = Calendar.getInstance()
    var _activeWeek: Int = _activeDate.get(Calendar.WEEK_OF_YEAR)
    val _today : Calendar = Calendar.getInstance()
    var _courses = listOf<Course>()
    var _todayCourses = listOf<Course>()

    val activeDate get() = _activeDate
    val activeWeek get() = _activeWeek
    val courses get() = _courses
    val todayCourses get() = _todayCourses

    fun updateCourses(coursesX: List<Course>){
        _courses = coursesX
    }

    fun filterTodaysCourses(course :  Course) : Boolean {

        val date = _activeDate
        val day = date.get(Calendar.DAY_OF_WEEK) - 1

        val since = Calendar.getInstance()
        val until = Calendar.getInstance()

        since.set(Calendar.YEAR, course.sinceYear)
        since.set(Calendar.MONTH, course.sinceMonth)
        since.set(Calendar.DAY_OF_MONTH, course.sinceDay)

        until.set(Calendar.YEAR, course.untilYear)
        until.set(Calendar.MONTH, course.untilMonth)
        until.set(Calendar.DAY_OF_MONTH, course.untilDay)

        if(date>until || date<since){
            return false
        }

        return when (day) {
            0 -> course.sunday
            1 -> course.monday
            2 -> course.tuesday
            3 -> course.wednesday
            4 -> course.thursday
            5 -> course.friday
            else -> course.saturday
        }
    }

    fun updateTodayCourses()  {
         _todayCourses = _courses.filter{filterTodaysCourses(it)}
    }

    fun updateActiveDate(date: Calendar){
        _activeDate = date
        updateTodayCourses()
    }

    fun prevWeek(){
        _activeWeek--
        activeDate.set(Calendar.WEEK_OF_YEAR, activeWeek)
        updateTodayCourses()
    }

    fun prevDay(){
        if(_activeDate.get(Calendar.DAY_OF_WEEK) == 1){ // sunday
            _activeWeek--
            _activeDate.set(Calendar.WEEK_OF_YEAR, activeWeek)
            _activeDate.set(Calendar.DAY_OF_WEEK, 7) // saturday
        }else{
            _activeDate.set(Calendar.DAY_OF_WEEK, _activeDate.get(Calendar.DAY_OF_WEEK) - 1)
        }
        updateTodayCourses()
    }

    fun nextDay(){
        if(_activeDate.get(Calendar.DAY_OF_WEEK) == 7){ // saturday
            _activeWeek++
            _activeDate.set(Calendar.WEEK_OF_YEAR, activeWeek)
            _activeDate.set(Calendar.DAY_OF_WEEK, 1) // sunday
        }else{
            _activeDate.set(Calendar.DAY_OF_WEEK, _activeDate.get(Calendar.DAY_OF_WEEK) + 1)
        }
        updateTodayCourses()
    }

    fun nextWeek(){
       _activeWeek++
        activeDate.set(Calendar.WEEK_OF_YEAR, activeWeek)
        updateTodayCourses()
    }

    fun goToToday(){
        _activeDate = _today
        updateTodayCourses()
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