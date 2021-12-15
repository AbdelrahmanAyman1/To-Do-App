package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.database.dao.TodoDAO
import com.example.todo.database.model.Converters
import com.example.todo.database.model.Todo

@Database(entities = [Todo::class], version = 2)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDAO

    companion object {
        val dataBaseName = "Todo-dataBase"
        private var myDatabase: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            if (myDatabase == null) {
                myDatabase = Room.databaseBuilder(
                    context,
                    TodoDatabase::class.java, dataBaseName
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return myDatabase!!

        }
    }
}
