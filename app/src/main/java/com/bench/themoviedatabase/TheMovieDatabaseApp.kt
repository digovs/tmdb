package com.bench.themoviedatabase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@ExperimentalComposeUiApi
@Composable
fun TheMovieComposableApp(){
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: MainDestinations.LOGIN
    TheMovieDatabaseNavGraph(
        navController = navController,
        startDestination = currentRoute
    )
}