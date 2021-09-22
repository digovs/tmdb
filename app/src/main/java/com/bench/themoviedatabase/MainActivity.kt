package com.bench.themoviedatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bench.themoviedatabase.remote.MoviesApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var moviesApi: MoviesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.IO).launch {
            moviesApi.getMovieList()
        }
    }
}