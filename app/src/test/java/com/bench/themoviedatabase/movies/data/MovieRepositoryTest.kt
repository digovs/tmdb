package com.bench.themoviedatabase.movies.data

import com.bench.themoviedatabase.BaseTestCase
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class MovieRepositoryTest : BaseTestCase() {

    private val movieDataSource = mock<MovieDataSource>()
    private val sut = MovieRepository(movieDataSource)

    @Test
    fun testGetMovieList() {
        runBlockingTest {
            sut.getMovieList()
            verify(movieDataSource).getMovieList()
        }
    }

    @Test
    fun testGetGenres() {
        runBlockingTest {
            sut.getGenres()
            verify(movieDataSource).getGenres()
        }
    }

    @Test
    fun testGetMovieListByGenreId() {
        runBlockingTest {
            sut.getMovieListByGenreId(1,2,3)
            verify(movieDataSource).getMovieListByGenreId(1,2,3)
        }
    }
}