package com.example.omdbapitestapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OneMovieResponse (
    @SerialName("Title") val title : String? = null,
    @SerialName("Year") val year : Int? = null,
    @SerialName("Rated") val rated : String? = null,
    @SerialName("Released") val released : String? = null,
    @SerialName("Runtime") val runtime : String? = null,
    @SerialName("Genre") val genre : String? = null,
    @SerialName("Director") val director : String? = null,
    @SerialName("Writer") val writer : String? = null,
    @SerialName("Actors") val actors : String? = null,
    @SerialName("Plot") val plot : String? = null,
    @SerialName("Language") val language : String? = null,
    @SerialName("Country") val country : String? = null,
    @SerialName("Awards") val awards : String? = null,
    @SerialName("Poster") val poster : String? = null,
    @SerialName("Ratings") val ratings : List<OneMovieRating>? = null,
    @SerialName("Metascore") val metascore : String? = null,
    @SerialName("imdbRating") val imdbRating : Double? = null,
    @SerialName("imdbVotes") val imdbVotes : Int? = null,
    @SerialName("imdbID") val imdbID : String? = null,
    @SerialName("Type") val type : String? = null,
    @SerialName("DVD") val dVD : String? = null,
    @SerialName("BoxOffice") val boxOffice : String? = null,
    @SerialName("Production") val production : String? = null,
    @SerialName("Website") val website : String? = null,
    @SerialName("Response") val response : Boolean? = null
)

@Serializable
data class OneMovieRating (
    @SerialName("Source") val source : String? = null,
    @SerialName("Value") val value : String? = null
)
