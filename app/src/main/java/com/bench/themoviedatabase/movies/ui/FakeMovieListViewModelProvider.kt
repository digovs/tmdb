package com.bench.themoviedatabase.movies.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.MovieItemsWithSection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


class FakeMovieListViewModelProvider() : PreviewParameterProvider<IMovieListViewModel> {
    override val values: Sequence<IMovieListViewModel> = sequenceOf(
        object : IMovieListViewModel {
            override val recentMovieItemsLiveData = MutableLiveData<List<MovieItem>>(
                listOf(
                    MovieItem(title = "title1", releaseDate = "release date 1"),
                    MovieItem(title = "title2", releaseDate = "release date 2"),
                    MovieItem(title = "title3", releaseDate = "release date 3")
                )
            )

            override val genresWithMoviesFlow: Flow<PagingData<MovieItemsWithSection>>
                // cannot preview paging data
                get() = emptyFlow()

        }
    )
}