package com.example.omdbapitestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbapitestapp.domain.MovieUseCase
import com.example.omdbapitestapp.model.ChangeFlagModel
import com.example.omdbapitestapp.model.UiMovieModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(
    private val search: MovieUseCase.Search,
    private val setWatchLater: MovieUseCase.WatchLater,
    private val setWatched: MovieUseCase.Watched,
) : ViewModel() {

    private val stateChannel = ConflatedBroadcastChannel<State?>(null)

    fun onSearch(query: String) {
        viewModelScope.launch {
            search(query)?.let { State(it) }?.let(stateChannel::offer)
        }
    }

    fun onWatchLater(id: String, watchLater: Boolean) = viewModelScope.launch {
        setWatchLater(ChangeFlagModel(id, watchLater))
    }

    fun onWatched(id: String, watched: Boolean) = viewModelScope.launch {
        setWatched(ChangeFlagModel(id, watched))
    }

    @FlowPreview
    fun observeState() = stateChannel.asFlow()

    data class State(
        val list: List<UiMovieModel> = emptyList()
    )

    override fun onCleared() {
        stateChannel.close()
        super.onCleared()
    }
}
