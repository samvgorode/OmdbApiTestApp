package com.example.omdbapitestapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OneMovieResponse (
    @SerialName("Title") val title : String,
    @SerialName("Year") val year : Int,
    @SerialName("Rated") val rated : String,
    @SerialName("Released") val released : String,
    @SerialName("Runtime") val runtime : String,
    @SerialName("Genre") val genre : String,
    @SerialName("Director") val director : String,
    @SerialName("Writer") val writer : String,
    @SerialName("Actors") val actors : String,
    @SerialName("Plot") val plot : String,
    @SerialName("Language") val language : String,
    @SerialName("Country") val country : String,
    @SerialName("Awards") val awards : String,
    @SerialName("Poster") val poster : String,
    @SerialName("Ratings") val ratings : List<OneMovieRating>,
    @SerialName("Metascore") val metascore : String,
    @SerialName("imdbRating") val imdbRating : Double,
    @SerialName("imdbVotes") val imdbVotes : Int,
    @SerialName("imdbID") val imdbID : String,
    @SerialName("Type") val type : String,
    @SerialName("DVD") val dVD : String,
    @SerialName("BoxOffice") val boxOffice : String,
    @SerialName("Production") val production : String,
    @SerialName("Website") val website : String,
    @SerialName("Response") val response : Boolean
)

@Serializable
data class OneMovieRating (
    @SerialName("Source") val source : String,
    @SerialName("Value") val value : String
)
