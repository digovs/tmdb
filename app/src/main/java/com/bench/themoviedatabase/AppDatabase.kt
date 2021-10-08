package com.bench.themoviedatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bench.themoviedatabase.login.data.UserDao
import com.bench.themoviedatabase.login.data.model.LoggedInInfo
import com.bench.themoviedatabase.util.DateConverter


@Database(entities = arrayOf(LoggedInInfo::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}