package com.example.omdbapitestapp.domain

interface SearchQueryRepository {

    fun store(query: String)
    fun load(): String
}
