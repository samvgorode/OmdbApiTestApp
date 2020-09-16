package com.example.omdbapitestapp.domain

import com.example.omdbapitestapp.model.ChangeFlagModel
import com.example.omdbapitestapp.model.UiMovieModel

sealed class MovieUseCase {

    class Search(
        private val movieRepository: MovieRepository
    ) : InOutUseCase<String, List<UiMovieModel>?> {
        override suspend fun invoke(input: String): List<UiMovieModel>? =
            movieRepository.search(input)?.map {
                it.run { UiMovieModel(imdbID, year, title, type, posterLink, watchLater, watched) }
            }
    }

    class WatchLater(
        private val movieRepository: MovieRepository
    ) : InUseCase<ChangeFlagModel> {
        override suspend fun invoke(input: ChangeFlagModel) {
            movieRepository.setWatchLater(input.id, input.flag)
        }
    }

    class Watched(
        private val movieRepository: MovieRepository
    ) : InUseCase<ChangeFlagModel> {
        override suspend fun invoke(input: ChangeFlagModel) {
            movieRepository.setWatched(input.id, input.flag)
        }
    }
}

interface InOutUseCase<in I, out O> {
    suspend operator fun invoke(input: I): O
}

interface OutUseCase<out O> {
    suspend operator fun invoke(): O
}

interface InUseCase<in I> {
    suspend operator fun invoke(input: I)
}
