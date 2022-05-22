package com.example.shchoolmate

import androidx.room.PrimaryKey
import java.util.*

data class Schedule (
    @PrimaryKey(autoGenerate = true) val id: Integer,
    val initialHour: String,
    val duration: Float,
    val since: Date,
    val until: Date,
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean,
    val sunday: Boolean
    )