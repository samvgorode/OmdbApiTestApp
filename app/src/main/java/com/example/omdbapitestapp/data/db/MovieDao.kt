package com.example.omdbapitestapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table WHERE imdbID = :imdbID")
    suspend fun loadByID(imdbID: String): List<MovieEntity>

    @Query("SELECT * FROM movie_table WHERE imdbID IN (:imdbIDs)")
    suspend fun loadAllByIds(imdbIDs: List<String>): List<MovieEntity>

    @Update(onConflict = REPLACE)
    suspend fun update(movie: MovieEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Transaction
    suspend fun setWatchLater(id: String, value: Boolean) {
        val item = loadByID(id).firstOrNull()
        if(item == null) insert(MovieEntity(imdbID = id, watchLater = value))
        else update(item.copy(watchLater = value))
    }

    @Transaction
    suspend fun setWatched(id: String, value: Boolean) {
        val item = loadByID(id).firstOrNull()
        if(item == null) insert(MovieEntity(imdbID = id, watched = value))
        else update(item.copy(watched = value))
    }
}
