package com.bench.themoviedatabase.login.data

import com.bench.themoviedatabase.login.data.model.LoggedInInfo
import javax.inject.Inject

class LocalLoginDataSource @Inject constructor(
    val userDao: UserDao
) {
    suspend fun upsertUser(userInfo: LoggedInInfo){
        userDao.upsert(userInfo)
    }

    suspend fun getUser():LoggedInInfo?{
        return userDao.getAll().firstOrNull()
    }
}