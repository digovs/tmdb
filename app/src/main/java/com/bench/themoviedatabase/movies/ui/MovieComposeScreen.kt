package com.bench.themoviedatabase.movies.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.bench.themoviedatabase.R
import com.bench.themoviedatabase.movies.data.model.MovieItem

@Composable
@Preview
fun MovieScreen(
    @PreviewParameter(FakeMovieListViewModelProvider::class) viewModel: IMovieListViewModel
) {
    val listOfMovies = viewModel.movieItemsLiveData.observeAsState()
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(stringResource(R.string.movies_list_recent_release_title))
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .padding(vertical = 20.dp)
            ) {
                listOfMovies.value?.forEach { movieItem ->
                    item {
                        MovieItem(movieItem = movieItem)
                    }
                }

            }
        }
    }
}

@Composable
fun MovieItem(movieItem: MovieItem) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxHeight()
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .width(150.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberImagePainter(movieItem.getFullPosterPath()),
                    contentDescription = movieItem.title
                )
                Text(text = movieItem.title, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(text = movieItem.releaseDate)
            }

        }
    }

}
