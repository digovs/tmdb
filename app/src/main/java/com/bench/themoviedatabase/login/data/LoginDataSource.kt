package com.bench.themoviedatabase.login.data

import com.bench.themoviedatabase.login.data.model.LoggedInInfo
import com.bench.themoviedatabase.login.data.model.LoginPost
import com.bench.themoviedatabase.login.data.model.RequestTokenPost
import com.bench.themoviedatabase.remote.MoviesApi
import kotlinx.coroutines.delay
import java.io.IOException
import javax.inject.Inject

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource @Inject constructor(val moviesApi: MoviesApi) {

    suspend fun login(username: String, password: String): Result<LoggedInInfo> {
        try {
            val token = moviesApi.getRequestToken()
            val loginBody = LoginPost(
                username = username,
                password = password,
                requestToken = token.requestToken
            )
            val loginToken = moviesApi.approveTokenWithLogin(loginBody = loginBody)
            val sessionResponse =
                moviesApi.createSession(requestTokenBody = RequestTokenPost(requestToken = loginToken.requestToken))
            // TODO: handle loggedInUser authentication
            val loggedInInfo = LoggedInInfo(
                username = username,
                password = password,
                sessionId = sessionResponse.sessionId,
                expiresAt = loginToken.expiresAt
            )
            return Result.Success(loggedInInfo)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}