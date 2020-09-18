package com.example.omdbapitestapp.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

abstract class BaseViewModel<S> : ViewModel() {

    private val stateChannel = ConflatedBroadcastChannel<S?>(null)

    fun observeState() = stateChannel.asFlow()

    protected fun offerState(state: S?) {
        stateChannel.offer(state)
    }

    protected fun loadStateValue(): S? = stateChannel.valueOrNull

    override fun onCleared() {
        stateChannel.close(Throwable("viewModel ${this::class.java.simpleName} cleared"))
        super.onCleared()
    }
}
