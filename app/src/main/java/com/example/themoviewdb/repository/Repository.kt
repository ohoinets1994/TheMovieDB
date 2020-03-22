package com.example.themoviewdb.repository

import com.example.themoviewdb.model.Genre
import com.example.themoviewdb.model.Movie
import com.example.themoviewdb.repository.database.GenreMovieCrossReference

interface Repository {
    suspend fun fetchPopularMovies(offlineOnly: Boolean = false): List<Movie>
    suspend fun fetchMovieGenre(offlineOnly: Boolean = false): List<Genre>
    suspend fun getGenreWithMovies(): List<Pair<Genre, List<Movie>>>

    suspend fun insert(item: Any)
    suspend fun delete(item: Any)
    suspend fun insertGenreWithMovie(join: GenreMovieCrossReference)
}