package com.bench.themoviedatabase.login.ui

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bench.themoviedatabase.MainApplication
import com.bench.themoviedatabase.R
import com.bench.themoviedatabase.login.data.LoginRepository
import com.bench.themoviedatabase.login.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _loginForm = MutableStateFlow(LoginFormState())
    val loginFormState: StateFlow<LoginFormState> = _loginForm

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    private val _snackbarState = MutableStateFlow<SnackbarState>(SnackbarState.Inactive())
    val snackbarState: StateFlow<SnackbarState> = _snackbarState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginUiState.emit(LoginUiState.Loading)

            val result = loginRepository.login(username, password)
            if (result is Result.Success) {
                _loginUiState.emit(LoginUiState.Success(userView = LoggedInUserView(displayName = result.data.displayName)))
                _snackbarState.emit(SnackbarState.Active(message = "Welcome!"))

            } else {
                _loginUiState.emit(LoginUiState.Error(msgResId = R.string.login_failed))
                _snackbarState.emit(
                    SnackbarState.Active(
                        message = getApplication<MainApplication>().getString(
                            R.string.login_failed
                        )
                    )
                )
            }
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

    //Set snackbar to inactive
    fun setSnackbarInactive() {
        viewModelScope.launch {
            _snackbarState.emit(SnackbarState.Inactive())
        }
    }
}