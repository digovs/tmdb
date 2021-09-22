package com.bench.themoviedatabase.movies.data.model

data class ResultWithPage<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_result: Int
)