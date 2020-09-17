package com.example.omdbapitestapp.data

import com.example.omdbapitestapp.domain.MovieRepository
import com.example.omdbapitestapp.model.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val remoteSource: MovieRemoteResource,
    private val localSource: MovieLocalResource,
) : MovieRepository {

    override suspend fun search(query: String): List<MovieModel>? = withContext(Dispatchers.IO) {
        val modelsFromSearch = remoteSource.search(query)?.search ?: return@withContext null
        val ids = modelsFromSearch.map { it?.imdbID }.filterNotNull()
        val localModels = localSource.loadAllByIds(ids)
        return@withContext modelsFromSearch.map { searchItem ->
            val localItem = localModels.find { it.imdbID == searchItem?.imdbID }
            val watchLater = localItem?.watchLater ?: false
            val watched = localItem?.watched ?: false
            MovieModel(
                imdbID = searchItem?.imdbID.orEmpty(),
                year = searchItem?.year.orEmpty(),
                title = searchItem?.title.orEmpty(),
                type = searchItem?.type.orEmpty(),
                posterLink = searchItem?.poster.orEmpty(),
                watchLater = watchLater,
                watched = watched,
            )
        }
    }

    override suspend fun setWatchLater(id: String, value: Boolean) = withContext(Dispatchers.IO) {
        localSource.setWatchLater(id, value)
    }

    override suspend fun setWatched(id: String, value: Boolean) = withContext(Dispatchers.IO) {
        localSource.setWatched(id, value)
    }
}
