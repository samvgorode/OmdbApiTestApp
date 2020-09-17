package com.example.omdbapitestapp.model

data class MovieModel(
    val imdbID: String,
    val year: String?,
    val title: String?,
    val type: String?,
    val posterLink: String?,
    val watchLater: Boolean = false,
    val watched: Boolean = false,
)
