package com.bench.themoviedatabase.movies.data

import androidx.paging.PagingSource
import com.bench.themoviedatabase.BaseTestCase
import com.bench.themoviedatabase.movies.data.model.GenreItem
import com.bench.themoviedatabase.movies.data.model.Genres
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.ResultWithPage
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class MoviePagingSourceTest : BaseTestCase() {

    private val movieRepository = mock<MovieRepository>()
    private val sut = MoviePagingSource(movieRepository = movieRepository)

    private val loadParams = mock<PagingSource.LoadParams<Int>>()
    private val genres = listOf(
        GenreItem(1,"1"),
        GenreItem(2,"2"),
        GenreItem(3,"3"),

        GenreItem(4,"4"),
        GenreItem(5,"5"),
        GenreItem(6,"6"),

        GenreItem(7,"7"),
        GenreItem(8,"8"),
    )
    private val resultWithPage = mock<ResultWithPage<MovieItem>>()
    private val movieItems = listOf<MovieItem>(
        mock{ whenever(it.title).thenReturn("movie1")},
        mock{ whenever(it.title).thenReturn("movie2")},
        mock{ whenever(it.title).thenReturn("movie3")},
        mock{ whenever(it.title).thenReturn("movie4")},
    )

    @Before
    fun before(){
        whenever(resultWithPage.results).thenReturn(movieItems)
      runBlocking {
          whenever(movieRepository.getGenres()).thenReturn(Genres(genres))
          whenever(movieRepository.getMovieListByGenreId(anyVararg())).thenReturn(resultWithPage)
      }
    }

    @Test
    fun `load - first page - load movies from first 3 genres`() {
        whenever(loadParams.key).thenReturn(1)

        runBlockingTest {
            sut.load(loadParams)

            verify(movieRepository).getGenres()
            verify(movieRepository).getMovieListByGenreId(1)
            verify(movieRepository).getMovieListByGenreId(2)
            verify(movieRepository).getMovieListByGenreId(3)
            verifyNoMoreInteractions(movieRepository)
        }
    }

    @Test
    fun `load - second page - load movies from the next 3 genres`() {
        whenever(loadParams.key).thenReturn(2)

        runBlockingTest {
            sut.load(loadParams)

            verify(movieRepository).getGenres()
            verify(movieRepository).getMovieListByGenreId(4)
            verify(movieRepository).getMovieListByGenreId(5)
            verify(movieRepository).getMovieListByGenreId(6)
            verifyNoMoreInteractions(movieRepository)
        }
    }
}