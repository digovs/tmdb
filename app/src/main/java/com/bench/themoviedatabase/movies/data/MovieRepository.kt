package com.bench.themoviedatabase.movies.data

import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieDataSource: MovieDataSource) {
    suspend fun getMovieList() = movieDataSource.getMovieList()

    suspend fun getGenres() = movieDataSource.getGenres()

    suspend fun getMovieListByGenreId(vararg id: Int) = movieDataSource.getMovieListByGenreId(*id)
}