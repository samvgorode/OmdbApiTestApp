package com.example.omdbapitestapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.android.ext.koin.androidContext

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDb: RoomDatabase(), Dao {
    abstract override fun movieDao(): MovieDao

    companion object {
        fun initialize(context: Context) =
            Room.databaseBuilder(
                context,
                MovieDb::class.java, "movieDatabase.db").build()
    }
}
