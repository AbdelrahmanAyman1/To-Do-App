package com.example.todo.database.model

import android.util.Log
import java.util.Calendar


fun Calendar.clearTimeFromDate(): Calendar {


    this.clear(Calendar.HOUR)
    this.clear(Calendar.SECOND)
    this.clear(Calendar.MINUTE)
    this.clear(Calendar.MILLISECOND)
    this.clear(Calendar.HOUR_OF_DAY)


    Log.e("clear time ", "" + this.time.time)


    return this
}
