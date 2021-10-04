package com.bench.themoviedatabase.movies.data

import com.bench.themoviedatabase.BaseTestCase
import com.bench.themoviedatabase.remote.MoviesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class MovieDataSourceTest : BaseTestCase() {

    private val moviesApi = mock<MoviesApi>()
    private val sut = MovieDataSource(moviesApi)

    @Test
    fun testGetMovieList() {
        runBlockingTest {
            sut.getMovieList()
            verify(moviesApi).getMovieList()
        }
    }

    @Test
    fun testGetGenres() {
        runBlockingTest {
            sut.getGenres()
            verify(moviesApi).getGenres()
        }
    }

    @Test
    fun testGetMovieListByGenreId() {
        runBlockingTest {
            sut.getMovieListByGenreId(1,2,3)
            verify(moviesApi).getMovieList("1,2,3")
        }
    }
}