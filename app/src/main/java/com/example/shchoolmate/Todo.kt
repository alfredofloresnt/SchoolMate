package com.example.shchoolmate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val comments: String,
    val date: String,
)
