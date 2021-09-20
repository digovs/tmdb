package com.bench.themoviedatabase.login.ui

/**
 * Data validation state of the login screen.
 */
sealed class LoginUiState {
    data class Error(val msg: String) : LoginUiState()
    data class Success(val userView: LoggedInUserView) : LoginUiState()
    object Loading : LoginUiState()
    object Idle : LoginUiState()
}

data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
