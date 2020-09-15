package com.example.omdbapitestapp.data

import com.example.omdbapitestapp.data.network.ApiClient
import com.example.omdbapitestapp.data.network.ApiClient.Companion.API_SEARCH_KEY
import com.example.omdbapitestapp.model.SearchResponse
import io.ktor.client.request.parameter

class MovieRemoteResource(private val apiClient: ApiClient) {

    suspend fun search(query: String): SearchResponse? = apiClient.get{
        parameter(API_SEARCH_KEY, query)
    }
}
