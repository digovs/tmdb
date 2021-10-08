package com.bench.themoviedatabase.login.data.model

import com.google.gson.annotations.SerializedName

data class LoginPost(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("request_token") val requestToken: String
)