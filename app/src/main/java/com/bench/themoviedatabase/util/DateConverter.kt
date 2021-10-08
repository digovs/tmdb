package com.bench.themoviedatabase.util

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter{
    companion object{
        const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss zz"
    }
    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.CANADA)
    @TypeConverter
    fun fromTimestamp(value: String): Date {
        return dateFormat.parse(value)!!
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): String {
        return dateFormat.format(date)
    }
}