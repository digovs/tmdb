package com.bench.themoviedatabase.login.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.bench.themoviedatabase.R
import com.bench.themoviedatabase.login.data.LoginRepository
import com.bench.themoviedatabase.login.data.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableStateFlow(LoginFormState())
    val loginFormState: StateFlow<LoginFormState> = _loginForm

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    fun login(username: String, password: String) {
        _loginUiState.value = LoginUiState.Loading

        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginUiState.value =
                LoginUiState.Success(userView = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginUiState.value = LoginUiState.Error(msgResId = R.string.login_failed)
        }

        _loginUiState.value = LoginUiState.Idle
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}