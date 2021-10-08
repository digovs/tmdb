package com.bench.themoviedatabase.login.data

import androidx.room.*
import com.bench.themoviedatabase.login.data.model.LoggedInInfo

@Dao
interface UserDao {
    @Query("SELECT * FROM loggedInInfo")
    suspend fun getAll(): List<LoggedInInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: LoggedInInfo)

    @Query("DELETE FROM loggedInInfo")
    suspend fun deleteAll()
}