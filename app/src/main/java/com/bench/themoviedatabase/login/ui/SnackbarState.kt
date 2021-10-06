package com.bench.themoviedatabase.login.ui

sealed class SnackbarState {
    data class Active(val message: String) : SnackbarState()
    class Inactive() : SnackbarState()
}
