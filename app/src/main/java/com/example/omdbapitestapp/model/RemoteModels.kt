package com.example.omdbapitestapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("Search") val search: List<SearchItem?>?,
    @SerialName("totalResults") val totalResults: Int?,
    @SerialName("Response") val response: Boolean?,
    @SerialName("Error") val error: String?,
)

@Serializable
data class SearchItem(
    @SerialName("Title") val title: String?,
    @SerialName("Year") val year: Int?,
    @SerialName("imdbID") val imdbID: String?,
    @SerialName("Type") val type: String?,
    @SerialName("Poster") val poster: String?,
)

