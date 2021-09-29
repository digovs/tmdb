package com.bench.themoviedatabase.movies.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.MovieItemsWithSection
import kotlinx.coroutines.flow.Flow

interface IMovieListViewModel {
    val recentMovieItemsLiveData : MutableLiveData<List<MovieItem>>
    val genresWithMoviesFlow: Flow<PagingData<MovieItemsWithSection>>
}