package com.example.shchoolmate

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CoursesViewModel(private val courseDao: CourseDao, private val weighingDao: WeighingDao): ViewModel() {

    // Weighing Dao functions
    suspend fun getAllCourses() = courseDao.getAllCourses()

    fun getCourse(id: Int): Course = courseDao.getCourse(id)

    suspend fun insertCourse(course: Course) = courseDao.insertCourse(course)

    fun deleteCourse(course: Course) = courseDao.deleteCourse(course)

    fun updateCourse(course: Course) = courseDao.updateCourse(course)

    // Weighing Dao functions
    suspend fun getAllWeighing(): List<Weighing> = weighingDao.getAllWeighing()

    suspend fun getWeighingsByIdCourse(id: Int): List<Weighing> = weighingDao.getWeighingsByIdCourse(id)

    suspend fun getWeighingsByIdCourseAndName(id: Int, name: String): Weighing = weighingDao.getWeighingsByIdCourseAndName(id, name)

    suspend fun updateWeight(id: Int, value: Double) = weighingDao.updateWeight(id, value)
    suspend fun updateWeightByCourseName(id: Int, name: String, value: Double) = weighingDao.updateWeightByCourseName(id, name, value)

    suspend fun updateActivityWeight(id: Int, value: Double) = weighingDao.updateActivityWeight(id, value)

    suspend fun insertWeighing(weighing: Weighing) = weighingDao.insertWeighing(weighing)

    fun deleteWeighing(weighing: Weighing) = weighingDao.deleteWeighing(weighing)

    fun updateWeighing(weighing: Weighing) = weighingDao.updateWeighing(weighing)

    suspend fun getActivityScoreByIdWeighing(id: Int, name: String): List<ActivityScore> = weighingDao.getActivityScoreByIdWeighing(id, name)

    suspend fun insertActivityScore(activityScore: ActivityScore): Long = weighingDao.insertActivityScore(activityScore)

    var a = 0.0
    var b = 0.0

    fun setValA(value: Double) {
        a = value
    }

    fun setValB(value: Double) {
        b = value
    }


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

    fun calculateExams(examsWeight: Weighing, activitiesExams: List<ActivityScore>, weight: Double): Double {
        var total = 0.0
        for (item in activitiesExams) {
            total += item.weight * weight
        }
        total = total * (examsWeight.value / 100)
        return total
    }

}

class CoursesViewModelFactory (private val courseDao: CourseDao, private val weighingDao: WeighingDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoursesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoursesViewModel(courseDao, weighingDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}