package com.example.themoviewdb.repository.network

import com.example.themoviewdb.model.Genre
import com.example.themoviewdb.model.Movie
import com.example.themoviewdb.repository.Repository
import com.example.themoviewdb.repository.database.GenreMovieCrossReference

class NetworkRepository(private val api: TheMovieApi) : Repository {
    override suspend fun fetchPopularMovies(offlineOnly: Boolean): List<Movie> =
        api.fetchPopularMovies().await().results

    override suspend fun fetchMovieGenre(offlineOnly: Boolean): List<Genre> =
        api.fetchMovieGenre().await().genres

    override suspend fun getGenreWithMovies(): List<Pair<Genre, List<Movie>>> = listOf()

    override suspend fun insert(item: Any) {}

    override suspend fun delete(item: Any) {}

    override suspend fun insertGenreWithMovie(join: GenreMovieCrossReference) {}

}