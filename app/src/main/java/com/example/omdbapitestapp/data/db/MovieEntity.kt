package com.example.omdbapitestapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey val imdbID: String,
    @ColumnInfo(name = "year") val year: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "posterLink") val posterLink: String?,
)
