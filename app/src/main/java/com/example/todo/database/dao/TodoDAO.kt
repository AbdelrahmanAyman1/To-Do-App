package com.example.todo.database.dao

import androidx.room.*
import com.example.todo.database.model.Todo
import java.util.*


@Dao
interface TodoDAO {

    @Insert
    fun insertTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)


    @Query("select * from Todo")
    fun getAllTodos(): MutableList<Todo>


    @Query("select * from Todo where date = :date")
    fun getTodoByDay(date: Long): MutableList<Todo>
}