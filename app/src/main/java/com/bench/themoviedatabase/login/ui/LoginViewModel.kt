package com.bench.themoviedatabase.login.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bench.themoviedatabase.R
import com.bench.themoviedatabase.login.data.LoginRepository
import com.bench.themoviedatabase.login.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState constructor(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false,
    val showLoading: Boolean = false,
    val loginStatus: LoginStatus? = null
)

sealed class LoginStatus{
    class Success(val name: String): LoginStatus()
    class Fail: LoginStatus()
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginUiState.update { it.copy(
                showLoading = true,
                loginStatus = null
            ) }

            val result = loginRepository.login(username, password)

            // delay to simulate network call
            delay(500)

            if (result is Result.Success) {
                _loginUiState.update { it.copy(
                    showLoading = false,
                    loginStatus = LoginStatus.Success(result.data.displayName)
                )}

            } else {
                _loginUiState.update { it.copy(
                    showLoading = false,
                    loginStatus = LoginStatus.Fail()
                )}
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        viewModelScope.launch {
            val validUserName = isUserNameValid(username)
            val validPassword = isPasswordValid(password)
            if (!validUserName && !validPassword) {
                _loginUiState.update { it.copy(
                    usernameError = R.string.invalid_username,
                    passwordError = R.string.invalid_password,
                    isDataValid = false
                )}
            } else if (!validPassword) {
                _loginUiState.update { it.copy(
                    usernameError = null,
                    passwordError = R.string.invalid_password,
                    isDataValid = false
                )}
            } else if (!validUserName){
                _loginUiState.update { it.copy(
                    usernameError = R.string.invalid_username,
                    passwordError = null,
                    isDataValid = false
                )}
            } else {
                _loginUiState.update { it.copy(
                    usernameError = null,
                    passwordError = null,
                    isDataValid = true
                )}
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