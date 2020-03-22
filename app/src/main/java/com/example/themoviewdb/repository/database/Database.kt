package com.example.themoviewdb.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class, GenreEntity::class, GenreMovieCrossReference::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun genresDao(): GenresDao
    abstract fun genreWithMoviesDao(): GenreWithMovieDao
}