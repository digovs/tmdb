package com.bench.themoviedatabase

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bench.themoviedatabase.login.ui.LoginScreen
import com.bench.themoviedatabase.login.ui.LoginViewModel
import com.bench.themoviedatabase.movies.ui.MovieListViewModel
import com.bench.themoviedatabase.movies.ui.MovieScreen

object MainDestinations{
    const val LOGIN = "LOGIN"
    const val MOVIES = "MOVIES"
}

@ExperimentalComposeUiApi
@Composable
fun TheMovieDatabaseNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.LOGIN
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.MOVIES) {
            val viewModel = hiltViewModel<MovieListViewModel>()
            MovieScreen(viewModel = viewModel)
        }

        composable(MainDestinations.LOGIN){
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(loginViewModel = viewModel)
        }
    }
}