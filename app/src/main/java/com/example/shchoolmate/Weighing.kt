package com.example.shchoolmate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weighing(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val idCourse: Int,
    val name: String,
    val value: Double
)
