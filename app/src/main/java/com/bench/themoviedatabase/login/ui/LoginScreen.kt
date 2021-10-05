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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bench.themoviedatabase.R

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel()
) {
    val loginUiState = loginViewModel.loginUiState.collectAsState()
    LoginLayout(
        loginUiState = loginUiState.value,
        onLoginFormChanged = loginViewModel::loginDataChanged,
        onLogin = loginViewModel::login
    )
}

@ExperimentalComposeUiApi
@Composable
fun LoginLayout(
    loginUiState: LoginUiState,
    onLoginFormChanged: (String, String) -> Unit,
    onLogin: (String, String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    if (loginUiState.loginStatus is LoginStatus.Fail) {
        val message = stringResource(id = R.string.login_failed)
        LaunchedEffect(scaffoldState.snackbarHostState){
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    } else if (loginUiState.loginStatus is LoginStatus.Success) {
        val messageTemplate = stringResource(id = R.string.welcome)
        LaunchedEffect(scaffoldState.snackbarHostState){
            scaffoldState.snackbarHostState.showSnackbar(
                message = String.format(messageTemplate, loginUiState.loginStatus.name),
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            LoginForm(
                loginUiState = loginUiState,
                onLoginFormChanged = onLoginFormChanged,
                onLogin = onLogin
            )
            if (loginUiState.showLoading) {
                CircularProgressIndicator()
            }
        }

    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginForm(
    loginUiState: LoginUiState,
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
            isError = loginUiState.usernameError != null
        )
        if (loginUiState.usernameError != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = loginUiState.usernameError),
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
            isError = loginUiState.passwordError != null
        )
        if (loginUiState.passwordError != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = loginUiState.passwordError),
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
                enabled = loginUiState.isDataValid,
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
        loginUiState = LoginUiState(
            isDataValid = true
        ),
        onLoginFormChanged = lambda,
        onLogin = lambda
    )
}

@ExperimentalComposeUiApi
@Composable
@Preview(showSystemUi = true)
fun PreviewLoginLayoutWithError() {
    val lambda = { text1: String, text2: String -> }
    LoginLayout(
        loginUiState = LoginUiState(
            usernameError = R.string.invalid_username,
            passwordError = R.string.invalid_password,
            isDataValid = false
        ),
        onLoginFormChanged = lambda,
        onLogin = lambda
    )
}

@ExperimentalComposeUiApi
@Composable
@Preview(showSystemUi = true)
fun PreviewLoadingLoginLayout() {
    val lambda = { text1: String, text2: String -> }
    LoginLayout(
        loginUiState = LoginUiState(
            isDataValid = true,
            showLoading = true
        ),
        onLoginFormChanged = lambda,
        onLogin = lambda
    )
}
