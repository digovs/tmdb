package com.bench.themoviedatabase.movies.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider


class FakeMovieListViewModelProvider() : PreviewParameterProvider<IMovieListViewModel> {
    override val values: Sequence<IMovieListViewModel> = sequenceOf(FakeMovieListViewModel())
}