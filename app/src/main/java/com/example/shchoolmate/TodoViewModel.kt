package com.example.shchoolmate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TodoViewModel(private val todoDao: TodoDao): ViewModel() {
    suspend fun getAllTodos() = todoDao.getAllTodos()

    fun getTodo(id: Int): Todo = todoDao.getTodo(id)

    suspend fun insertTodo(todo: Todo) = todoDao.insertTodo(todo)

    fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)
}
class TodosViewModelFactory(private val todoDao: TodoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(todoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}