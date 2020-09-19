package com.example.omdbapitestapp.presentation.details

import androidx.lifecycle.viewModelScope
import com.example.omdbapitestapp.domain.MovieUseCase
import com.example.omdbapitestapp.model.ChangeFlagModel
import com.example.omdbapitestapp.model.FullMovieModel
import com.example.omdbapitestapp.model.OneMovieResponseModel.Failure
import com.example.omdbapitestapp.model.OneMovieResponseModel.Success
import com.example.omdbapitestapp.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    loadMovieId: MovieUseCase.LoadMovieId,
    private val loadOneMovie: MovieUseCase.LoadOne,
    private val setWatchLater: MovieUseCase.WatchLater,
    private val setWatched: MovieUseCase.Watched,
) : BaseViewModel<MovieDetailsViewModel.State>() {

    init {
        val selectedId = loadMovieId()
        if (selectedId.isNotBlank()) {
            viewModelScope.launch {
                val state: State = when (val movieResponse = loadOneMovie(selectedId)) {
                    is Success -> State(movie = movieResponse.movie)
                    is Failure -> State(errorMessage = movieResponse.errorMessage)
                }
                offerState(state)
            }
        }
    }

    fun onWatchLater(id: String, watchLater: Boolean) {
        viewModelScope.launch {
            setWatchLater(ChangeFlagModel(id, watchLater))
        }
        updateState { model -> model.copy(watchLater = watchLater) }
    }

    fun onWatched(id: String, watched: Boolean) {
        viewModelScope.launch {
            setWatched(ChangeFlagModel(id, watched))
        }
        updateState { model -> model.copy(watched = watched) }
    }

    private inline fun updateState(block: (FullMovieModel) -> FullMovieModel) {
        val stateValue = loadStateValue()
        stateValue?.movie?.let {
            offerState(stateValue.copy(movie = block(it)))
        }
    }

    data class State(
        val movie: FullMovieModel? = null,
        val errorMessage: String? = null
    )
}
