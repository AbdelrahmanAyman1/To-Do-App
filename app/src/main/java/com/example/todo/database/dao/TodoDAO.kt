package com.example.todo.database.dao

import androidx.room.*
import com.example.todo.database.model.Todo

@Dao
interface TodoDAO {
    @Insert
    fun addTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("select * from Todo")
    fun getAllTodo(): List<Todo>
}