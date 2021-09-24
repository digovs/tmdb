package com.bench.themoviedatabase.movies.ui

import androidx.lifecycle.MutableLiveData
import com.bench.themoviedatabase.movies.data.model.MovieItem

interface IMovieListViewModel {
    val movieItemsLiveData : MutableLiveData<List<MovieItem>>
}