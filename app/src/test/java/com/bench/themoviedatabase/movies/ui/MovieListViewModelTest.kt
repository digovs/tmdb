package com.bench.themoviedatabase.movies.ui

import com.bench.themoviedatabase.BaseTestCase
import com.bench.themoviedatabase.movies.data.MoviePagingSource
import com.bench.themoviedatabase.movies.data.MovieRepository
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.ResultWithPage
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class MovieListViewModelTest : BaseTestCase() {
    private val movieRepository = mock<MovieRepository>()
    private lateinit var sut: MovieListViewModel
    private val movieItem1 = mock<MovieItem>()
    private val movieItem2 = mock<MovieItem>()
    private val movieItem3 = mock<MovieItem>()

    @Before
    fun before(){
        whenever(movieItem1.releaseDate).thenReturn("1")
        whenever(movieItem2.releaseDate).thenReturn("2")
        whenever(movieItem3.releaseDate).thenReturn("3")
        runBlockingTest {
            whenever(movieRepository.getMovieList()).thenReturn(
                ResultWithPage<MovieItem>(
                    page = 1,
                    totalPages = 100,
                    totalResult = 300,
                    results = listOf(
                        movieItem1,
                        movieItem2,
                        movieItem3
                    )
                )
            )
            sut = MovieListViewModel(testDispatcherPovider, movieRepository, MoviePagingSource(movieRepository))
        }
    }

    @Test
    fun `init - get recent release`(){
        runBlockingTest {
            verify(movieRepository).getMovieList()
        }
    }

    @Test
    fun `refresh - get sorted recent release`(){
        runBlockingTest {
            verify(movieRepository, times(1)).getMovieList()

            sut.refreshMovies()
            verify(movieRepository, times(2)).getMovieList()
            assertThat(sut.recentMovieItemsLiveData.value!!.map { it.releaseDate }, `is`(listOf("3", "2", "1")))
        }
    }
}