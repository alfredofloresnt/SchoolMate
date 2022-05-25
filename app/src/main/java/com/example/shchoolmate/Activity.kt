package com.example.shchoolmate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val idWeighing: Int,
    val name: String,
    val score: Float,
    val weight: Float
    )
