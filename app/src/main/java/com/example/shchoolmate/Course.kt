package com.example.shchoolmate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val location: String,
    //val schedule: Schedule,
)