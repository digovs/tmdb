package com.bench.themoviedatabase.movies.ui

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.MovieItemsWithSection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class FakeMovieListViewModel: IMovieListViewModel {
    override val recentMovieItemsLiveData = MutableLiveData<List<MovieItem>>()
    override val genresWithMoviesFlow: Flow<PagingData<MovieItemsWithSection>>
        get() = emptyFlow()
}