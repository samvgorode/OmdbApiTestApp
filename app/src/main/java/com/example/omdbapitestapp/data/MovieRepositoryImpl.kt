package com.example.omdbapitestapp.data

import android.util.Log
import com.example.omdbapitestapp.domain.MovieRepository
import com.example.omdbapitestapp.model.FullMovieModel
import com.example.omdbapitestapp.model.MovieModel
import com.example.omdbapitestapp.model.OneMovieResponseModel
import com.example.omdbapitestapp.model.SearchResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val remoteSource: MovieRemoteResource,
    private val localSource: MovieLocalResource,
) : MovieRepository {

    override suspend fun search(query: String): SearchResponseModel = withContext(Dispatchers.IO) {
        val searchResult = remoteSource.search(query)
        val modelsFromSearch = searchResult?.search
        if (searchResult?.response == false || modelsFromSearch == null) SearchResponseModel.Failure(
            searchResult?.error
        )
        else {
            val ids = modelsFromSearch.map { it?.imdbID }.filterNotNull()
            Log.e("MovieRepositoryImpl", "response ids + $ids")
            val localModels = localSource.loadAllByIds(ids)
            val localIsEmpty = localModels.isEmpty()
            SearchResponseModel.Success(modelsFromSearch.map { searchItem ->
                val watchLater: Boolean
                val watched: Boolean
                if (localIsEmpty) {
                    watchLater = false
                    watched = false
                } else {
                    val localItem = localModels.find { it.imdbID == searchItem?.imdbID }
                    watchLater = localItem?.watchLater ?: false
                    watched = localItem?.watched ?: false
                }
                MovieModel(
                    imdbID = searchItem?.imdbID.orEmpty(),
                    year = searchItem?.year.orEmpty(),
                    title = searchItem?.title.orEmpty(),
                    type = searchItem?.type.orEmpty(),
                    posterLink = searchItem?.poster.orEmpty(),
                    watchLater = watchLater,
                    watched = watched,
                )
            })
        }

    }

    override suspend fun getMovieById(imdbId: String): OneMovieResponseModel =
        withContext(Dispatchers.IO) {
            val result = remoteSource.getMovieById(imdbId)
            delay(400)
            if (result?.response == false || result == null)
                OneMovieResponseModel.Failure(result?.error)
            else result.run {
                val localModel = localSource.loadById(imdbId).firstOrNull()
                OneMovieResponseModel.Success(
                    FullMovieModel(
                        imdbId = imdbId,
                        title = title,
                        year = year,
                        type = type,
                        imdbRating = imdbRating,
                        imdbVotes = imdbVotes,
                        poster = poster,
                        released = released,
                        country = country,
                        language = language,
                        director = director,
                        production = production,
                        genre = genre,
                        runtime = runtime,
                        actors = actors,
                        plot = plot,
                        watchLater = localModel?.watchLater ?: false,
                        watched = localModel?.watched ?: false
                    )
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
