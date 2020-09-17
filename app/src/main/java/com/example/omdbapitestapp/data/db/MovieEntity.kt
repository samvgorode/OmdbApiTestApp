package com.example.omdbapitestapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey val imdbID: String,
    @ColumnInfo(name = "watchLater") val watchLater: Boolean = false,
    @ColumnInfo(name = "watched") val watched: Boolean = false,
)
