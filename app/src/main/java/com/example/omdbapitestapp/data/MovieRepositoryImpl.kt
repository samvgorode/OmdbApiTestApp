package com.example.omdbapitestapp.data

import com.example.omdbapitestapp.domain.MovieRepository
import com.example.omdbapitestapp.model.SearchResponse

class MovieRepositoryImpl(private val remoteSource: MovieRemoteResource): MovieRepository {

    override suspend fun search(query: String): SearchResponse? = remoteSource.search(query)
}
