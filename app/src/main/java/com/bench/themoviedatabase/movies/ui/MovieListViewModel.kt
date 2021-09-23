package com.bench.themoviedatabase.movies.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bench.themoviedatabase.movies.data.MovieRepository
import com.bench.themoviedatabase.movies.data.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
)  : ViewModel(), IMovieListViewModel {

    override val movieItemsLiveData = MutableLiveData<List<MovieItem>>()

    init {
        refreshMovies()
    }

    fun refreshMovies(){
        viewModelScope.launch(Dispatchers.IO){
            movieItemsLiveData.postValue(sortMoviesByReleaseDate(movieRepository.getMovieList().results))
        }
    }

    private fun sortMoviesByReleaseDate(movies: List<MovieItem>): List<MovieItem>{
        return movies.sortedByDescending { it.releaseDate }
    }
}