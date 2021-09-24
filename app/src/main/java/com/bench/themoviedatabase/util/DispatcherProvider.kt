package com.bench.themoviedatabase.util

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatcherProvider @Inject constructor(){
    fun getIO() = Dispatchers.IO
    fun getDefault() = Dispatchers.Default
}