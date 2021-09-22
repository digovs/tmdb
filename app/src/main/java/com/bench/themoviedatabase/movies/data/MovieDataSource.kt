package com.bench.themoviedatabase.movies.data

import com.bench.themoviedatabase.BuildConfig
import com.bench.themoviedatabase.remote.MoviesApi
import javax.inject.Inject

/**
 *
 */
class MovieDataSource @Inject constructor(private val moviesApi: MoviesApi) {
    suspend fun getMovieList() = moviesApi.getMovieList()
}