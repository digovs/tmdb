package com.bench.themoviedatabase.movies.data.model

data class ResultWithPage<T>(
    val page: Int,
    val results: List<T>,
    val totalPages: Int,
    val totalResult: Int
)