package com.example.todo.database.model

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun toMilliSeconds(date: Date?): Long {
        return date?.time ?: 0
    }

    @TypeConverter
    fun fromMiiSeconds(milliseconds: Long): Date {
        return Date(milliseconds)
    }
}