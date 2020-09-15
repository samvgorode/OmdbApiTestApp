package com.example.omdbapitestapp.domain

import com.example.omdbapitestapp.model.SearchResponse

interface MovieRepository {
    suspend fun search(query: String): SearchResponse?
}
