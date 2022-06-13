package com.example.shchoolmate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActivityScore(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val idWeighing: Int,
    val name: String,
    val score: Double,
    val weight: Double
)

