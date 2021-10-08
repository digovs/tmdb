package com.bench.themoviedatabase.login.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class TokenResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("expires_at") val expiresAt: Date,
    @SerializedName("request_token") val requestToken: String
)
