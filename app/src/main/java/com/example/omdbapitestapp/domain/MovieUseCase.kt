package com.example.omdbapitestapp.domain

import com.example.omdbapitestapp.model.ChangeFlagModel
import com.example.omdbapitestapp.model.MovieModel

sealed class MovieUseCase {

    class StoreQuery(
        private val queryRepository: SearchQueryRepository
    ) : InUseCase<String> {
        override fun invoke(input: String) =
            queryRepository.store(input)
    }

    class LoadQuery(
        private val queryRepository: SearchQueryRepository
    ) : OutUseCase<String> {
        override fun invoke(): String =
            queryRepository.load()
    }

    class Search(
        private val movieRepository: MovieRepository
    ) : InOutCoUseCase<String, List<MovieModel>?> {
        override suspend fun invoke(input: String): List<MovieModel>? =
            movieRepository.search(input)
    }

    class WatchLater(
        private val movieRepository: MovieRepository
    ) : InCoUseCase<ChangeFlagModel> {
        override suspend fun invoke(input: ChangeFlagModel) {
            movieRepository.setWatchLater(input.id, input.flag)
        }
    }

    class Watched(
        private val movieRepository: MovieRepository
    ) : InCoUseCase<ChangeFlagModel> {
        override suspend fun invoke(input: ChangeFlagModel) {
            movieRepository.setWatched(input.id, input.flag)
        }
    }
}

interface InOutCoUseCase<in I, out O> {
    suspend operator fun invoke(input: I): O
}

interface OutCoUseCase<out O> {
    suspend operator fun invoke(): O
}

interface OutUseCase<out O> {
    operator fun invoke(): O
}

interface InCoUseCase<in I> {
    suspend operator fun invoke(input: I)
}

interface InUseCase<in I> {
    operator fun invoke(input: I)
}
