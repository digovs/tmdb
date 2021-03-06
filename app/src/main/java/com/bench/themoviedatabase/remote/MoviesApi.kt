package com.bench.themoviedatabase.remote

import com.bench.themoviedatabase.movies.data.model.Genres
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.ResultWithPage
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface representing the movie API service.
 */
interface MoviesApi {
    @GET("3/discover/movie")
    suspend fun getMovieList(
        @Query(value = "with_genres") genre: String = ""
    ): ResultWithPage<MovieItem>

    @GET("3/genre/movie/list")
    suspend fun getGenres(): Genres
}