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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.bench.themoviedatabase.R
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.MovieItemsWithSection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun MovieScreen(
    viewModel: MovieListViewModel,
    navController: NavHostController
) {
    MovieContent(
        movies = viewModel.recentMovieItemsLiveData,
        genreSections = viewModel.genresWithMoviesFlow
    )
}

@Composable
fun MovieContent(
    movies: LiveData<List<MovieItem>>,
    genreSections: Flow<PagingData<MovieItemsWithSection>>
) {
    val listOfMovies = movies.observeAsState()
    val lazyMovieItems = genreSections.collectAsLazyPagingItems()
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

@Composable
@Preview
fun PreviewHomeContent() {
    val recentMovieItemsLiveData = MutableLiveData<List<MovieItem>>(
        listOf(
            MovieItem(title = "title1", releaseDate = "release date 1"),
            MovieItem(title = "title2", releaseDate = "release date 2"),
            MovieItem(title = "title3", releaseDate = "release date 3")
        )
    )

    // cannot preview paging data
    val genresWithMoviesFlow: Flow<PagingData<MovieItemsWithSection>> = emptyFlow()

    MovieContent(movies = recentMovieItemsLiveData, genreSections = genresWithMoviesFlow)
}
