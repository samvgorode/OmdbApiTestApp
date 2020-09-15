package com.example.omdbapitestapp.domain

import com.example.omdbapitestapp.data.db.MovieEntity

interface MovieRepository {
    suspend fun search(query: String): List<MovieEntity>?
    suspend fun setWatchLater(id: String, value: Boolean)
    suspend fun setWatched(id: String, value: Boolean)
}
