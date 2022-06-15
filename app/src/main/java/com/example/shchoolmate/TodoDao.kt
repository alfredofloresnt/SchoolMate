package com.example.shchoolmate

import androidx.room.*

@Dao
interface TodoDao {
    // Get all todos
    @Query("SELECT * FROM Todo")
    suspend fun getAllTodos(): List<Todo>

    // Get single todo by id
    @Query("SELECT * FROM Todo WHERE id = :id")
    fun getTodo(id: Int): Todo

    // Insert todo
    @Insert
    suspend fun insertTodo(todo: Todo)

    // Delete todo
    @Delete
    fun deleteTodo(todo: Todo)

    // Update todo
    @Update
    fun updateTodo(todo: Todo)
}