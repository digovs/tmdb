package com.bench.themoviedatabase.movies.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bench.themoviedatabase.movies.data.MoviePagingSource
import com.bench.themoviedatabase.movies.data.MovieRepository
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.MovieItemsWithSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val moviePagingSource: MoviePagingSource
)  : ViewModel(), IMovieListViewModel {

    override val recentMovieItemsLiveData = MutableLiveData<List<MovieItem>>()
    override val genresWithMoviesFlow: Flow<PagingData<MovieItemsWithSection>>
        get() = Pager(PagingConfig(3)) { moviePagingSource }.flow

    init {
        refreshMovies()
    }

    fun refreshMovies(){
        viewModelScope.launch(Dispatchers.IO){
            recentMovieItemsLiveData.postValue(sortMoviesByReleaseDate(movieRepository.getMovieList().results))
        }
    }

    private fun sortMoviesByReleaseDate(movies: List<MovieItem>): List<MovieItem>{
        return movies.sortedByDescending { it.releaseDate }
    }
}