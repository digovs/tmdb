package com.bench.themoviedatabase.login.ui

import androidx.annotation.StringRes

/**
 * Data validation state of the login screen.
 */
sealed class LoginUiState {
    data class Error(@StringRes val msgResId: Int) : LoginUiState()
    data class Success(val userView: LoggedInUserView) : LoginUiState()
    object Loading : LoginUiState()
    object Idle : LoginUiState()
}
