package com.example.omdbapitestapp.model

sealed class SearchResponseModel{
    data class Success(val movies: List<MovieModel>?): SearchResponseModel()
    data class Failure(val errorMessage: String?): SearchResponseModel()
}

data class MovieModel(
    val imdbID: String,
    val year: String?,
    val title: String?,
    val type: String?,
    val posterLink: String?,
    val watchLater: Boolean = false,
    val watched: Boolean = false,
    val errorMessage: String? = null
)
