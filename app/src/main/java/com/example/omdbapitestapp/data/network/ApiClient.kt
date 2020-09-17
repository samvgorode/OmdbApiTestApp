package com.example.omdbapitestapp.data.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.takeFrom

class ApiClient(val httpClient: HttpClient) {

    suspend inline fun <reified T> get(
        noinline block: HttpRequestBuilder.() -> Unit = {}
    ): T? =
        try {
            httpClient.get { call(block = block) }
        } catch (e: Throwable) {
            Log.e("ApiClient", e.message ?: e.localizedMessage)
            null
        }

    fun HttpRequestBuilder.call(
        block: HttpRequestBuilder.() -> Unit
    ) {
        url.takeFrom(API_URL)
        accept(ContentType.Application.Json)
        parameter(API_KEY_NAME, API_KEY)
        block()
    }

    companion object {
        private const val API_KEY = "8dbb671d"
        private const val API_KEY_NAME = "apikey"
        private const val API_URL = "http://www.omdbapi.com/"
        const val API_SEARCH_KEY = "s"
        const val API_ID_KEY = "i"
    }
}
