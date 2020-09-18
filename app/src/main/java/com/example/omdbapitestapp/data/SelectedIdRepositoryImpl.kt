package com.example.omdbapitestapp.data

import com.example.omdbapitestapp.domain.SelectedIdRepository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

class SelectedIdRepositoryImpl : SelectedIdRepository {

    private val values = ConflatedBroadcastChannel("")

    override fun store(query: String) {
        values.offer(query)
    }

    override fun load(): String = values.value
}
