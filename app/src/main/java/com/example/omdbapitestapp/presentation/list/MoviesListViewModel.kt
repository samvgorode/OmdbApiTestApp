package com.example.omdbapitestapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbapitestapp.domain.MovieUseCase
import com.example.omdbapitestapp.model.ChangeFlagModel
import com.example.omdbapitestapp.model.MovieModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MoviesListViewModel(
    private val search: MovieUseCase.Search,
    private val setWatchLater: MovieUseCase.WatchLater,
    private val setWatched: MovieUseCase.Watched,
    private val loadQuery: MovieUseCase.LoadQuery,
) : ViewModel() {

    init { updateSearch() }

    private fun updateSearch() {
        val query = loadQuery()
        viewModelScope.launch {
            search(query)?.let { State(it) }?.let(stateChannel::offer)
        }
    }

    private val stateChannel = ConflatedBroadcastChannel<State?>(null)

    fun onWatchLater(id: String, watchLater: Boolean) {
        viewModelScope.launch {
            setWatchLater(ChangeFlagModel(id, watchLater))
        }
        updateChannel(id) { model-> model.copy(watchLater = watchLater) }
    }

    fun onWatched(id: String, watched: Boolean) {
        viewModelScope.launch {
            setWatched(ChangeFlagModel(id, watched))
        }
        updateChannel(id) { model-> model.copy(watched = watched) }
    }

    private fun updateChannel(id: String, block: (MovieModel)->MovieModel) {
        stateChannel.valueOrNull?.list?.toMutableList()?.let { list->
            val item = list.find { it.imdbID == id }
            val index = list.indexOf(item)
            item?.let { list.set(index, block(it)) }
            stateChannel.offer(State(list = list))
    }}

    @FlowPreview
    fun observeState() = stateChannel.asFlow()

    data class State(
        val list: List<MovieModel> = emptyList()
    )

    override fun onCleared() {
        stateChannel.close()
        super.onCleared()
    }
}
