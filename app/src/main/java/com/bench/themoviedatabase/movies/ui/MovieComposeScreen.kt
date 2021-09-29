package com.bench.themoviedatabase.movies.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.paging.compose.collectAsLazyPagingItems
import com.bench.themoviedatabase.R
import com.bench.themoviedatabase.movies.data.model.MovieItemsWithSection

@Composable
@Preview
fun MovieScreen(
    @PreviewParameter(FakeMovieListViewModelProvider::class) viewModel: IMovieListViewModel
) {
    val listOfMovies = viewModel.recentMovieItemsLiveData.observeAsState()
    val lazyMovieItems = viewModel.genresWithMoviesFlow.collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        content = {
            item {
                MovieItemsRowWithSectionTitle(
                    movieItemsWithSection = MovieItemsWithSection(
                        stringResource(
                            R.string.movies_list_recent_release_title
                        ),
                        listOfMovies.value
                    )
                )
            }
            items(lazyMovieItems.itemCount) { index ->
                lazyMovieItems[index]?.let { movieItemsWithSection ->
                    MovieItemsRowWithSectionTitle(movieItemsWithSection = movieItemsWithSection)
                }
            }
        })
}
