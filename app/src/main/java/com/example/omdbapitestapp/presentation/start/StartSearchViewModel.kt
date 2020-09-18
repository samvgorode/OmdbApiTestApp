package com.example.omdbapitestapp.presentation.start

import com.example.omdbapitestapp.domain.MovieUseCase
import com.example.omdbapitestapp.presentation.base.BaseViewModel

class StartSearchViewModel(
    private val storeQuery: MovieUseCase.StoreQuery,
    private val loadQuery: MovieUseCase.LoadQuery,
) : BaseViewModel<StartSearchViewModel.State>() {

    init {
        val query = loadQuery()
        if (query.isNotBlank()) offerState(State(query))
    }

    fun saveQuery(query: String) {
        storeQuery(query)
    }

    data class State(val query: String = "")
}
