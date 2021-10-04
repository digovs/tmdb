package com.bench.themoviedatabase.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bench.themoviedatabase.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.movie_list_fragment, container, false)
        val movieList = root.findViewById<ComposeView>(R.id.movies_compose_view)
        movieList.setContent {
            MovieScreen(viewModel)
        }
        return root
    }
}