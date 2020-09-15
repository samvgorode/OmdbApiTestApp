package com.example.omdbapitestapp.di

import com.example.omdbapitestapp.data.MovieRemoteResource
import com.example.omdbapitestapp.data.MovieRepositoryImpl
import com.example.omdbapitestapp.data.network.ApiClient
import com.example.omdbapitestapp.domain.MovieRepository
import com.example.omdbapitestapp.presentation.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single { ApiClient(get()) }
    single { MovieRemoteResource(get()) }
    single {
        HttpClient(OkHttp) {
            install(JsonFeature) { serializer = KotlinxSerializer() }
        }
    }
}
