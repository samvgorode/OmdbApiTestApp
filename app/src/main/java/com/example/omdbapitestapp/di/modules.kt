package com.example.omdbapitestapp.di

import com.example.omdbapitestapp.data.MovieLocalResource
import com.example.omdbapitestapp.data.MovieRemoteResource
import com.example.omdbapitestapp.data.MovieRepositoryImpl
import com.example.omdbapitestapp.data.db.Dao
import com.example.omdbapitestapp.data.db.MovieDb
import com.example.omdbapitestapp.data.network.ApiClient
import com.example.omdbapitestapp.domain.MovieRepository
import com.example.omdbapitestapp.presentation.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.ContentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.nonstrict
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
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

