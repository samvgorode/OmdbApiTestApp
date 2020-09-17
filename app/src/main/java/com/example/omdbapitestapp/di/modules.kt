package com.example.omdbapitestapp.di

import com.example.omdbapitestapp.data.MovieLocalResource
import com.example.omdbapitestapp.data.MovieRemoteResource
import com.example.omdbapitestapp.data.MovieRepositoryImpl
import com.example.omdbapitestapp.data.SearchQueryRepositoryImpl
import com.example.omdbapitestapp.data.SelectedIdRepositoryImpl
import com.example.omdbapitestapp.data.db.Dao
import com.example.omdbapitestapp.data.db.MovieDb
import com.example.omdbapitestapp.data.network.ApiClient
import com.example.omdbapitestapp.domain.MovieRepository
import com.example.omdbapitestapp.domain.MovieUseCase
import com.example.omdbapitestapp.domain.SearchQueryRepository
import com.example.omdbapitestapp.domain.SelectedIdRepository
import com.example.omdbapitestapp.presentation.list.MoviesListViewModel
import com.example.omdbapitestapp.presentation.start.StartSearchViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.ContentType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val appModule = module {
    viewModel { MoviesListViewModel(get(), get(), get(), get(), get()) }
    viewModel { StartSearchViewModel(get()) }
    factory { MovieUseCase.Search(get()) }
    factory { MovieUseCase.Watched(get()) }
    factory { MovieUseCase.WatchLater(get()) }
    factory { MovieUseCase.StoreQuery(get()) }
    factory { MovieUseCase.LoadQuery(get()) }
    factory { MovieUseCase.StoreMovieId(get()) }
    factory { MovieUseCase.LoadMovieId(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<SearchQueryRepository> { SearchQueryRepositoryImpl() }
    single<SelectedIdRepository> { SelectedIdRepositoryImpl() }
    single { ApiClient(get()) }
    single { MovieRemoteResource(get()) }
    single { MovieLocalResource(get()) }
    single { get<Dao>().movieDao() }
    single<Dao> { MovieDb.initialize(androidContext()) }
    single {
        HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json {
                    isLenient = true
                    ignoreUnknownKeys = false
                    allowSpecialFloatingPointValues = true
                    useArrayPolymorphism = false
                })
                acceptContentTypes = listOf(ContentType.Application.Json, ContentType.Text.Plain)
            }
        }
    }
}

