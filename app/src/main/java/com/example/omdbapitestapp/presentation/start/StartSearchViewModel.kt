package com.example.omdbapitestapp.presentation.start

import androidx.lifecycle.ViewModel
import com.example.omdbapitestapp.domain.MovieUseCase

class StartSearchViewModel(
    private val storeQuery: MovieUseCase.StoreQuery
): ViewModel() {

    fun saveQuery(query: String) {
        storeQuery(query)
    }
}
