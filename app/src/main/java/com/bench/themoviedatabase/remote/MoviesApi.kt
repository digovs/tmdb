package com.bench.themoviedatabase.remote

import com.bench.themoviedatabase.login.data.model.LoginPost
import com.bench.themoviedatabase.login.data.model.RequestTokenPost
import com.bench.themoviedatabase.login.data.model.SessionResponse
import com.bench.themoviedatabase.login.data.model.TokenResponse
import com.bench.themoviedatabase.movies.data.model.Genres
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.ResultWithPage
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("3/authentication/token/new")
    suspend fun getRequestToken(): TokenResponse

    @POST("3/authentication/token/validate_with_login")
    suspend fun approveTokenWithLogin(@Body loginBody: LoginPost): TokenResponse

    @POST("3/authentication/session/new")
    suspend fun createSession(@Body requestTokenBody: RequestTokenPost): SessionResponse
}