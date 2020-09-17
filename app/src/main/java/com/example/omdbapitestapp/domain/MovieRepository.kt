package com.example.omdbapitestapp.domain

import com.example.omdbapitestapp.model.MovieModel

interface MovieRepository {
    suspend fun search(query: String): List<MovieModel>?
    suspend fun setWatchLater(id: String, value: Boolean)
    suspend fun setWatched(id: String, value: Boolean)
}
