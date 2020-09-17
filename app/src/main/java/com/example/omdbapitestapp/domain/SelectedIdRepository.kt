package com.example.omdbapitestapp.domain

interface SelectedIdRepository {

    fun store(query: String)
    fun load(): String
}
