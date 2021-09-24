package com.bench.themoviedatabase.movies.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bench.themoviedatabase.movies.data.model.GenreItem
import com.bench.themoviedatabase.movies.data.model.MovieItemsWithSection
import javax.inject.Inject


class MoviePagingSource @Inject constructor(
    private val movieRepository: MovieRepository
): PagingSource<Int, MovieItemsWithSection>(){

    companion object{
        const val SECTIONS_PER_PAGE = 3
    }

    private var genres = emptyList<GenreItem>()

    override fun getRefreshKey(state: PagingState<Int, MovieItemsWithSection>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemsWithSection> {
        val nextPage = params.key ?: 1
        if (genres.isEmpty()){
            genres = movieRepository.getGenres().genres
        }
        val mutableList = mutableListOf<MovieItemsWithSection>()
        val minIndex = (nextPage - 1) * SECTIONS_PER_PAGE
        val maxIndex = nextPage * SECTIONS_PER_PAGE - 1
        for (index in minIndex .. maxIndex){
            if (index < genres.size){
                val moviesResponse =
                    movieRepository.getMovieListByGenreId(genres[index].id)
                mutableList.add(MovieItemsWithSection(genres[index].name, moviesResponse.results))
            }
        }

        return LoadResult.Page(
            data = mutableList,
            prevKey =
            if (nextPage == 1) null
            else nextPage - 1,
            nextKey = nextPage.plus(1)
        )
    }

}