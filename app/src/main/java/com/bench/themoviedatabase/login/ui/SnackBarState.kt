package com.bench.themoviedatabase.login.ui

import com.bench.themoviedatabase.R

sealed class SnackBarState() {

    data class SnackBarActiveState(
        val message: String = "",
        val messageId: Int = R.string.login_failed
    ) : SnackBarState()

    class SnackBarInactiveState() : SnackBarState()
}

