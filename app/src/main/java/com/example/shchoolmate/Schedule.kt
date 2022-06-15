package com.example.shchoolmate

import androidx.room.PrimaryKey
import android.icu.util.Calendar

data class Schedule (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val initialHour: Int,
    val initialMin: Int,
    val endHour: Int,
    val endMin: Int,
    val since: Calendar,
    val until: Calendar,
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean,
    val sunday: Boolean
    )