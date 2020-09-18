package com.example.omdbapitestapp.domain

import com.example.omdbapitestapp.model.ChangeFlagModel
import com.example.omdbapitestapp.model.CheckFlagsModel
import com.example.omdbapitestapp.model.OneMovieResponseModel
import com.example.omdbapitestapp.model.SearchResponseModel

sealed class MovieUseCase {

    class StoreQuery(
        private val queryRepository: SearchQueryRepository
    ) : UseCase<String, Unit> {
        override fun invoke(input: String) =
            queryRepository.store(input)
    }

    class LoadQuery(
        private val queryRepository: SearchQueryRepository
    ) : OutUseCase<String> {
        override fun invoke(): String =
            queryRepository.load()
    }

    class StoreMovieId(
        private val queryRepository: SelectedIdRepository
    ) : UseCase<String, Unit> {
        override fun invoke(input: String) =
            queryRepository.store(input)
    }

    class LoadMovieId(
        private val queryRepository: SelectedIdRepository
    ) : OutUseCase<String> {
        override fun invoke(): String =
            queryRepository.load()
    }

    class Search(
        private val movieRepository: MovieRepository
    ) : CoUseCase<String, SearchResponseModel> {
        override suspend fun invoke(input: String): SearchResponseModel =
            movieRepository.search(input)
    }

    class LoadOne(
        private val movieRepository: MovieRepository
    ) : CoUseCase<String, OneMovieResponseModel> {
        override suspend fun invoke(input: String): OneMovieResponseModel =
            movieRepository.getMovieById(input)
    }

    class WatchLater(
        private val movieRepository: MovieRepository
    ) : CoUseCase<ChangeFlagModel, Unit> {
        override suspend fun invoke(input: ChangeFlagModel) {
            movieRepository.setWatchLater(input.id, input.flag)
        }
    }

    class Watched(
        private val movieRepository: MovieRepository
    ) : CoUseCase<ChangeFlagModel, Unit> {
        override suspend fun invoke(input: ChangeFlagModel) {
            movieRepository.setWatched(input.id, input.flag)
        }
    }

    class LoadCheckFlags(
        private val movieRepository: MovieRepository
    ) : CoUseCase<List<String>, List<CheckFlagsModel>> {
        override suspend fun invoke(input: List<String>): List<CheckFlagsModel> =
            movieRepository.getCheckFlagsModel(input)
    }
}
