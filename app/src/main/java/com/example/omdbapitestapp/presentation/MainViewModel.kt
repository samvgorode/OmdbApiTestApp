package com.example.omdbapitestapp.presentation

import androidx.lifecycle.ViewModel
import com.example.omdbapitestapp.domain.MovieRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository): ViewModel() {

    fun loadData() {
        viewModelScope.launch {
            val list = movieRepository.search("fuck")
            movieRepository.setWatchLater("tt0486585", true)
            movieRepository.setWatched("tt0486585", true)
            val list2 = movieRepository.search("fuck")
        }
    }
}
