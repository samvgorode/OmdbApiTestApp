package com.example.omdbapitestapp.data

import com.example.omdbapitestapp.domain.SearchQueryRepository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

class SearchQueryRepositoryImpl: SearchQueryRepository {

    private val values = ConflatedBroadcastChannel("")

    override fun store(query: String) {
        values.offer(query)
    }

    override fun load(): String = values.value
}
