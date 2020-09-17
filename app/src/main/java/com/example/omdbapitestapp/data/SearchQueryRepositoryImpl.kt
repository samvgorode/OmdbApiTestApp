package com.example.omdbapitestapp.data

import com.example.omdbapitestapp.domain.SearchQueryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

@ExperimentalCoroutinesApi
class SearchQueryRepositoryImpl: SearchQueryRepository {

    private val values = ConflatedBroadcastChannel<String>()

    override fun store(query: String) {
        values.offer(query)
    }

    override fun load(): String = values.value
}
