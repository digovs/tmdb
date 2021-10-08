package com.bench.themoviedatabase.login.data.model

import java.util.*

data class LoggedInInfo(
    val username: String,
    val password: String,
    val sessionId: String,
    val expiresAt: Date
)
