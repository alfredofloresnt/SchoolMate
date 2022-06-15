package com.example.shchoolmate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val location: String,
    val initialHour: Int,
    val initialMin: Int,
    val endHour: Int,
    val endMin: Int,
    val sinceYear: Int,
    val sinceMonth: Int,
    val sinceDay: Int,
    val untilYear: Int,
    val untilMonth: Int,
    val untilDay: Int,
    val sunday: Boolean,
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday : Boolean,
    val saturday: Boolean
)