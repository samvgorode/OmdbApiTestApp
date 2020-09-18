package com.example.omdbapitestapp.data

import com.example.omdbapitestapp.data.db.MovieDao
import com.example.omdbapitestapp.data.db.MovieEntity

class MovieLocalResource(private val movieDao: MovieDao) {

    suspend fun loadAllByIds(imdbIDs: List<String>): List<MovieEntity> =
        movieDao.loadAllByIds(imdbIDs)

    suspend fun loadById(imdbID: String): List<MovieEntity> =
        movieDao.loadByID(imdbID)

    suspend fun setWatchLater(id: String, value: Boolean) = movieDao.setWatchLater(id, value)

    suspend fun setWatched(id: String, value: Boolean) = movieDao.setWatched(id, value)

}
