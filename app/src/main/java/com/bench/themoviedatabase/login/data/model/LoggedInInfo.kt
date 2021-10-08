package com.bench.themoviedatabase.login.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class LoggedInInfo(
    @PrimaryKey val id: Int = 1,
    val username: String,
    val password: String,
    val sessionId: String,
    val expiresAt: Date
)
