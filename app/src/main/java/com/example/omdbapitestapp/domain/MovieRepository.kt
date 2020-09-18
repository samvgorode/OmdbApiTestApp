package com.example.omdbapitestapp.domain

import com.example.omdbapitestapp.model.OneMovieResponseModel
import com.example.omdbapitestapp.model.SearchResponseModel

interface MovieRepository {
    suspend fun search(query: String): SearchResponseModel
    suspend fun setWatchLater(id: String, value: Boolean)
    suspend fun setWatched(id: String, value: Boolean)
    suspend fun getMovieById(imdbId: String): OneMovieResponseModel
}
