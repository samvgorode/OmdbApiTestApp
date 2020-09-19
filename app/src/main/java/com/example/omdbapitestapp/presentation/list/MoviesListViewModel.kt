package com.example.omdbapitestapp.presentation.list

import androidx.lifecycle.viewModelScope
import com.example.omdbapitestapp.domain.MovieUseCase
import com.example.omdbapitestapp.model.ChangeFlagModel
import com.example.omdbapitestapp.model.MovieModel
import com.example.omdbapitestapp.model.SearchResponseModel
import com.example.omdbapitestapp.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val search: MovieUseCase.Search,
    private val setWatchLater: MovieUseCase.WatchLater,
    private val setWatched: MovieUseCase.Watched,
    private val loadQuery: MovieUseCase.LoadQuery,
    private val storeId: MovieUseCase.StoreMovieId,
    private val loadCheckFlags: MovieUseCase.LoadCheckFlags,
) : BaseViewModel<MoviesListViewModel.State>() {

    init {
        updateSearch()
    }

    private fun updateSearch() {
        val query = loadQuery()
        viewModelScope.launch {
            val state: State = when (val searchResponse = search(query)) {
                is SearchResponseModel.Success -> State(list = searchResponse.movies.orEmpty())
                is SearchResponseModel.Failure -> State(errorMessage = searchResponse.errorMessage)
            }
            offerState(state)
        }
    }

    fun onWatchLater(id: String, watchLater: Boolean) {
        viewModelScope.launch {
            setWatchLater(ChangeFlagModel(id, watchLater))
        }
        updateChannel(id) { model -> model.copy(watchLater = watchLater) }
    }

    fun onWatched(id: String, watched: Boolean) {
        viewModelScope.launch {
            setWatched(ChangeFlagModel(id, watched))
        }
        updateChannel(id) { model -> model.copy(watched = watched) }
    }

    fun onGoToDetailsFragment(id: String) {
        storeId(id)
        changeGoToDetailsFlag(true)
        changeGoToDetailsFlag(false)
    }

    private fun changeGoToDetailsFlag(goToDetails: Boolean) {
        offerState(loadStateValue()?.copy(goToMovieDetails = goToDetails))
    }

    private inline fun updateChannel(id: String, block: (MovieModel) -> MovieModel) {
        loadStateValue()?.list?.toMutableList()?.let { list ->
            val item = list.find { it.imdbID == id }
            val index = list.indexOf(item)
            item?.let { list.set(index, block(it)) }
            offerState(State(list = list))
        }
    }

    fun updateFlags() {
        viewModelScope.launch {
            loadStateValue()?.list?.run {
                val currentIds = map { it.imdbID }
                val checkModels = loadCheckFlags(currentIds)
                val newList = mutableListOf<MovieModel>()
                forEach { uiModel ->
                    checkModels.find { it.id == uiModel.imdbID }?.run {
                        if (watchLater != uiModel.watchLater || watched != uiModel.watched)
                            newList.add(uiModel.copy(watchLater = watchLater, watched = watched))
                        else newList.add(uiModel)
                    } ?: newList.add(uiModel)
                }
                offerState(State(newList))
            }
        }
    }

    data class State(
        val list: List<MovieModel> = emptyList(),
        val errorMessage: String? = null,
        val goToMovieDetails: Boolean = false,
    )
}
