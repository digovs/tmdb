package com.bench.themoviedatabase.movies.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.bench.themoviedatabase.R
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.MovieItemsWithSection

@Composable
@Preview(showBackground = true)
fun MovieItemsRowWithSectionTitle(
    @PreviewParameter(MovieItemsWithSectionPreviewDataProvider::class) movieItemsWithSection: MovieItemsWithSection
) {
    Text(movieItemsWithSection.sectionName)
    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        movieItemsWithSection.movies?.forEach { movieItem ->
            item {
                MovieItem(movieItem = movieItem)
            }
        }

    }
}

@Composable
fun MovieItem(
    movieItem: MovieItem
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(245.dp)
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberImagePainter(
                    data = movieItem.getFullPosterPath(),
                    builder = {
                        placeholder(R.drawable.movie_poster_placeholder)
                    }
                ),
                contentDescription = movieItem.title
            )
            Text(text = movieItem.title, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = movieItem.releaseDate)
        }

    }
}