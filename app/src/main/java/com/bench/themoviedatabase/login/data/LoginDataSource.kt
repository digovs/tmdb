package com.bench.themoviedatabase.login.data

import com.bench.themoviedatabase.login.data.model.LoggedInUser
import kotlinx.coroutines.delay
import java.io.IOException
import javax.inject.Inject

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource @Inject constructor() {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        delay(1000L)
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}