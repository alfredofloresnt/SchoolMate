package com.example.shchoolmate

import androidx.room.*

@Dao
interface WeighingDao {
    // Get all Weighing
    @Query("SELECT * FROM Weighing")
    suspend fun getAllWeighing(): List<Weighing>

    // Get single Weighing by idCurse
    @Query("SELECT * FROM Weighing WHERE idCourse = :id")
    suspend fun getWeighingsByIdCourse(id: Int): List<Weighing>

    // Get single Weighing by idCurse and name
    @Query("SELECT * FROM Weighing WHERE idCourse = :id AND name = :name")
    suspend fun getWeighingsByIdCourseAndName(id: Int, name: String): Weighing

    // Update single Weighing by idCourse and name
    @Query("UPDATE Weighing SET value = :value WHERE id = :id")
    suspend fun updateWeight(id: Int, value: Double)

    // Update single Weighing by idCourse and name
    @Query("UPDATE Weighing SET value = :value WHERE idCourse = :id AND name = :name")
    suspend fun updateWeightByCourseName(id: Int, name: String, value: Double)

    // Update single ActivityWeighing by idCurse and name
    @Query("UPDATE ActivityScore SET weight = :value WHERE id = :id")
    suspend fun updateActivityWeight(id: Int, value: Double)

    // Insert Weighing
    @Insert
    suspend fun insertWeighing(weighing: Weighing): Long

    // Delete Weighing
    @Delete
    fun deleteWeighing(weighing: Weighing)

    // Update Weighing
    @Update
    fun updateWeighing(weighing: Weighing)

    @Query("SELECT a.* FROM ActivityScore a JOIN Weighing w ON a.idWeighing = w.id WHERE w.idCourse = :id AND w.name = :name")
    suspend fun getActivityScoreByIdWeighing(id: Int, name: String): List<ActivityScore>

    // Insert Activity Score
    @Insert
    suspend fun insertActivityScore(activityScore: ActivityScore): Long

}