package com.example.omdbapitestapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("Search") val search: List<SearchItem?>? = null,
    @SerialName("totalResults") val totalResults: Int? = null,
    @SerialName("Response") val response: Boolean? = null,
    @SerialName("Error") val error: String? = null,
)
