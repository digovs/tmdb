package com.bench.themoviedatabase.login.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bench.themoviedatabase.R
import com.bench.themoviedatabase.login.data.LoginRepository
import com.bench.themoviedatabase.login.data.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableStateFlow(LoginFormState())
    val loginFormState: StateFlow<LoginFormState> = _loginForm

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginUiState.emit(LoginUiState.Loading)

            val result = loginRepository.login(username, password)
            if (result is Result.Success) {
                _loginUiState.emit(LoginUiState.Success(userView = LoggedInUserView(displayName = result.data.displayName)))

            } else {
                _loginUiState.emit(LoginUiState.Error(msgResId = R.string.login_failed))
            }

            delay(500)
            _loginUiState.emit(LoginUiState.Idle)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        viewModelScope.launch {
            if (!isUserNameValid(username)) {
                _loginForm.emit(LoginFormState(usernameError = R.string.invalid_username))
            } else if (!isPasswordValid(password)) {
                _loginForm.emit(LoginFormState(passwordError = R.string.invalid_password))
            } else {
                _loginForm.emit(LoginFormState(isDataValid = true))
            }
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