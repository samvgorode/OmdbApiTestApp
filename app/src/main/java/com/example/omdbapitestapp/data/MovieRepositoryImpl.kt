package com.example.omdbapitestapp.data

import com.example.omdbapitestapp.data.db.MovieEntity
import com.example.omdbapitestapp.domain.MovieRepository
import com.example.omdbapitestapp.model.SearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val remoteSource: MovieRemoteResource,
    private val localSource: MovieLocalResource,
) : MovieRepository {

    override suspend fun search(query: String): List<MovieEntity>? {
        val dbModels = remoteSource.search(query)?.search?.mapNotNull{ it.toDbModel() }?.apply {
            withContext(Dispatchers.IO) { localSource.insertAll(this@apply) }
        }
        return dbModels?.map { it.imdbID }?.run { localSource.loadAllByIds(this) }
    }

    override suspend fun setWatchLater(id: String, value: Boolean) = withContext(Dispatchers.IO) {
        localSource.setWatchLater(id, value)
    }


    override suspend fun setWatched(id: String, value: Boolean) = withContext(Dispatchers.IO) {
        localSource.setWatched(id, value)
    }

    private fun SearchItem?.toDbModel() = this?.run {
        MovieEntity(
            imdbID = imdbID.orEmpty(),
            year = year,
            title = title,
            type = type,
            posterLink = poster
        )
    }
}
