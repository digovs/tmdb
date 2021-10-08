package com.bench.themoviedatabase.login.data

import android.util.Log
import com.bench.themoviedatabase.login.data.model.LoggedInInfo
import com.bench.themoviedatabase.login.data.model.LoggedInUser
import com.bench.themoviedatabase.login.data.model.SessionInfo
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository @Inject constructor(
    val dataSource: LoginDataSource,
    val localLoginDataSource: LocalLoginDataSource
    ) {

    // in-memory cache of the loggedInUser object
    var user: SessionInfo? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<LoggedInInfo> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private suspend fun setLoggedInUser(loggedInUser: LoggedInInfo) {
        localLoginDataSource.upsertUser(loggedInUser)
        this.user = SessionInfo(sessionId = loggedInUser.sessionId, expiresAt = loggedInUser.expiresAt)
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}