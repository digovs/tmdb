package com.bench.themoviedatabase.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bench.themoviedatabase.R
import com.bench.themoviedatabase.login.data.LoginRepository
import com.bench.themoviedatabase.login.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    private val _loginForm = MutableStateFlow(LoginFormState())
    val loginFormState: StateFlow<LoginFormState> = _loginForm

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    private val _snackBarState =
        MutableStateFlow<SnackBarState>(SnackBarState.SnackBarInactiveState())
    val snackBarState: StateFlow<SnackBarState> = _snackBarState

    init {
        showSnackBar()
        setLoginIdleState()
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginUiState.emit(LoginUiState.Loading)
            Log.d(TAG, "login: Loading")

            val result = loginRepository.login(username, password)
            if (result is Result.Success) {
                _loginUiState.emit(LoginUiState.Success(userView = LoggedInUserView(displayName = result.data.displayName)))
                Log.d(TAG, "login: Success")

            } else {
                _loginUiState.emit(LoginUiState.Error(msgResId = R.string.login_failed))
            }
        }
    }
    // Sets the login state to idle after receiving success or error
    private fun setLoginIdleState() {
        viewModelScope.launch {
            _loginUiState.collect {
                when (it) {
                    is LoginUiState.Success -> {
                        _loginUiState.emit(LoginUiState.Idle)
                        Log.d(TAG, "login: Idle after success")
                    }
                    is LoginUiState.Error -> _loginUiState.emit(LoginUiState.Idle)
                    else -> {
                    }
                }
            }
        }
    }
    // Sets the snackBar to an active state with a message
    private fun showSnackBar() {
        viewModelScope.launch {
            _loginUiState.collect {
                when (it) {
                    is LoginUiState.Success -> _snackBarState.emit(
                        SnackBarState.SnackBarActiveState(
                            message = "Welcome!"
                        )
                    )
                    is LoginUiState.Error -> _snackBarState.emit(
                        SnackBarState.SnackBarActiveState(
                            messageId = R.string.login_failed
                        )
                    )
                    else -> {
                    }
                }
            }
        }

    }

    fun setSnackBarInactiveState() {
        viewModelScope.launch {
            _snackBarState.emit(SnackBarState.SnackBarInactiveState())
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

    companion object {
        private const val TAG = "LoginViewModel"
    }
}