package com.bench.themoviedatabase.movies.data

import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.ResultWithPage
import com.bench.themoviedatabase.remote.MoviesApi
import javax.inject.Inject

/**
 *
 */
class MovieDataSource @Inject constructor(private val moviesApi: MoviesApi) {
    suspend fun getMovieList() = moviesApi.getMovieList()

    suspend fun getGenres() = moviesApi.getGenres()

    suspend fun getMovieListByGenreId(vararg id: Int): ResultWithPage<MovieItem>{
        val idString = id.joinToString(",")
        return moviesApi.getMovieList(idString)
    }
}