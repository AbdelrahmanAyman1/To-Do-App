package com.example.todo.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    var name: String? = null,
    @ColumnInfo
    var description: String? = null,
    @ColumnInfo
    var date: Date? = null,
    @ColumnInfo
    var isDone: Boolean,

    )