package com.example.omdbapitestapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDb: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
