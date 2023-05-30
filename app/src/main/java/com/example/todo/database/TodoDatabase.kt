package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.database.dao.TodoDAO
import com.example.todo.database.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun getTodoDao(): TodoDAO

    companion object {

        var myDataBase: TodoDatabase? = null
        var NAME_OF_DATABASE: String = "Todo DataBase"

        fun getInstance(context: Context): TodoDatabase {


            if (myDataBase == null) {

                myDataBase =
                    Room.databaseBuilder(context, TodoDatabase::class.java, NAME_OF_DATABASE)
                        .allowMainThreadQueries()
                        .build()
            }

            return myDataBase!!
        }
    }
}