package com.bench.themoviedatabase.movies.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bench.themoviedatabase.movies.data.model.MovieItem
import com.bench.themoviedatabase.movies.data.model.MovieItemsWithSection

class MovieItemsWithSectionPreviewDataProvider : PreviewParameterProvider<MovieItemsWithSection> {
    private val movieItems = listOf(
        MovieItem(
            title = "title",
            releaseDate = "2021-09-15"
        ),
        MovieItem(
            title = "long long long long long long long long long long long long long title",
            releaseDate = "2021-09-10"
        )
    )

    override val values: Sequence<MovieItemsWithSection> = sequenceOf(
        MovieItemsWithSection("section title", movieItems)
    )
}