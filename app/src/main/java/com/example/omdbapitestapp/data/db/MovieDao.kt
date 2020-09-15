package com.example.omdbapitestapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table WHERE imdbID = :imdbID")
    fun getByID(imdbID: String): List<MovieEntity>

    @Insert
    fun insertAll(movies: List<MovieEntity>)

    @Delete
    fun delete(user: MovieEntity)
}
