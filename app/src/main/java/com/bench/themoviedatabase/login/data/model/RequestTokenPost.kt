package com.bench.themoviedatabase.login.data.model

import com.google.gson.annotations.SerializedName

data class RequestTokenPost(@SerializedName("request_token") val requestToken: String)