package com.example.omdbapitestapp.model

sealed class OneMovieResponseModel {
    data class Success(val movie: FullMovieModel?) : OneMovieResponseModel()
    data class Failure(val errorMessage: String?) : OneMovieResponseModel()
}

data class FullMovieModel(
    val imdbId: String? = null,
    val title: String? = null,
    val year: String? = null,
    val type: String? = null,
    val imdbRating: String? = null,
    val imdbVotes: String? = null,
    val poster: String? = null,
    val released: String? = null,
    val country: String? = null,
    val language: String? = null,
    val director: String? = null,
    val production: String? = null,
    val genre: String? = null,
    val runtime: String? = null,
    val actors: String? = null,
    val plot: String? = null,
    val watchLater: Boolean = false,
    val watched: Boolean = false,
)
