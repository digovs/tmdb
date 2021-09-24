package com.bench.themoviedatabase.movies.ui

import androidx.lifecycle.MutableLiveData
import com.bench.themoviedatabase.movies.data.model.MovieItem

class FakeMovieListViewModel: IMovieListViewModel {
    override val movieItemsLiveData = MutableLiveData<List<MovieItem>>()
}