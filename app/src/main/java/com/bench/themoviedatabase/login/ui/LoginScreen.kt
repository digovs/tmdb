package com.bench.themoviedatabase.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bench.themoviedatabase.R

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navigateToMovie: () -> Unit
) {
    val loginFormState = loginViewModel.loginFormState.collectAsState()
    val loginUiState = loginViewModel.loginUiState.collectAsState()
    val snackbarState = loginViewModel.snackbarState.collectAsState()
    LoginLayout(
        navigateToMovie = navigateToMovie,
        loginFormState = loginFormState.value,
        loginUiState = loginUiState.value,
        snackbarState = snackbarState.value,
        onLoginFormChanged = loginViewModel::loginDataChanged,
        onLogin = loginViewModel::login
    )
}

@ExperimentalComposeUiApi
@Composable
fun LoginLayout(
    loginFormState: LoginFormState,
    loginUiState: LoginUiState,
    onLoginFormChanged: (String, String) -> Unit,
    onLogin: (String, String) -> Unit,
    navigateToMovie: () -> Unit = {},
    snackbarState: SnackbarState,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    when (loginUiState) {
        is LoginUiState.Success -> {
            navigateToMovie()
        }
        is LoginUiState.Error -> {
        }
        else -> {
        }
    }
    if (snackbarState is SnackbarState.Active) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = snackbarState.message,
                duration = SnackbarDuration.Long
            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> viewModel.setSnackbarInactive()
                else -> {
                }
            }
        }
    }


    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (loginUiState == LoginUiState.Loading) {
                CircularProgressIndicator()
            } else {

                LoginForm(
                    loginFormState = loginFormState,
                    onLoginFormChanged = onLoginFormChanged,
                    onLogin = onLogin
                )
            }

        }

    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginForm(
    loginFormState: LoginFormState,
    onLoginFormChanged: (String, String) -> Unit,
    onLogin: (String, String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp, vertical = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(96.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {
                onLoginFormChanged(email, password)
                email = it
            },
            label = { Text(stringResource(id = R.string.prompt_email)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            ),
            isError = loginFormState.usernameError != null
        )
        if (loginFormState.usernameError != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = loginFormState.usernameError),
                color = Color.Red,
                textAlign = TextAlign.Start
            )
        }
        OutlinedTextField(
            value = password,
            onValueChange = {
                onLoginFormChanged(email, password)
                password = it
            },
            label = { Text(stringResource(id = R.string.prompt_password)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onLogin(email, password)
                    keyboardController?.hide()
                }
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.focusRequester(focusRequester),
            isError = loginFormState.passwordError != null
        )
        if (loginFormState.passwordError != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = loginFormState.passwordError),
                color = Color.Red,
                textAlign = TextAlign.Start
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                enabled = loginFormState.isDataValid,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onClick = {
                    keyboardController?.hide()
                    onLogin(email, password)
                }
            ) {
                Text(text = stringResource(id = R.string.action_sign_in))
            }
            Spacer(modifier = Modifier.weight(8f))
        }
    }
}

@ExperimentalComposeUiApi
@Composable
@Preview(showSystemUi = true)
fun PreviewLoginLayout() {
    val lambda = { text1: String, text2: String -> }
    LoginLayout(
        loginFormState = LoginFormState(
            isDataValid = true
        ),
        loginUiState = LoginUiState.Idle,
        onLoginFormChanged = lambda,
        onLogin = lambda,
        snackbarState = SnackbarState.Inactive()
    )
}

@ExperimentalComposeUiApi
@Composable
@Preview(showSystemUi = true)
fun PreviewLoginLayoutWithError() {
    val lambda = { text1: String, text2: String -> }
    LoginLayout(
        loginFormState = LoginFormState(
            usernameError = R.string.invalid_username,
            passwordError = R.string.invalid_password,
            isDataValid = false,
        ),
        loginUiState = LoginUiState.Idle,
        onLoginFormChanged = lambda,
        onLogin = lambda,
        snackbarState = SnackbarState.Inactive()
    )
}

@ExperimentalComposeUiApi
@Composable
@Preview(showSystemUi = true)
fun PreviewLoadingLoginLayout() {
    val lambda = { text1: String, text2: String -> }
    LoginLayout(
        loginFormState = LoginFormState(
            isDataValid = true
        ),
        loginUiState = LoginUiState.Idle,
        onLoginFormChanged = lambda,
        onLogin = lambda,
        snackbarState = SnackbarState.Inactive()
    )
}
