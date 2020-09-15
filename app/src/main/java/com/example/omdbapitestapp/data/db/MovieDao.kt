package com.example.omdbapitestapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table WHERE imdbID = :imdbID")
    suspend fun getByID(imdbID: String): List<MovieEntity>

    @Query("SELECT * FROM movie_table WHERE imdbID IN (:imdbIDs)")
    suspend fun loadAllByIds(imdbIDs: List<String>): List<MovieEntity>

    @Insert(onConflict = IGNORE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Insert(onConflict = IGNORE)
    suspend fun insert(movie: MovieEntity)

    @Transaction
    suspend fun setWatchLater(id: String, value: Boolean) {
        val item = getByID(id).firstOrNull()
        item?.run { insert(copy(watchLater = value)) }
    }

    @Transaction
    suspend fun setWatched(id: String, value: Boolean) {
        val item = getByID(id).firstOrNull()
        item?.run { insert(copy(watched = value)) }
    }
}
