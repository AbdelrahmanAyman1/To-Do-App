package com.example.todo.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int? = null,

    @ColumnInfo
    var ttile: String? = null,

    @ColumnInfo
    var details: String? = null,

    @ColumnInfo
    var date: Long? = null,

    @ColumnInfo
    var isDone: Boolean? = false
)