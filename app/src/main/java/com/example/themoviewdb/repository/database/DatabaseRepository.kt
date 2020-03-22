package com.example.themoviewdb.repository.database

import android.util.Log
import com.example.themoviewdb.model.Genre
import com.example.themoviewdb.model.Movie
import com.example.themoviewdb.repository.Repository

class DatabaseRepository (private val database: AppDatabase) : Repository {

    override suspend fun fetchPopularMovies(offlineOnly: Boolean): List<Movie> =
        database.moviesDao().getAllMovies().map { it.toMovie() }

    override suspend fun fetchMovieGenre(offlineOnly: Boolean): List<Genre> =
        database.genresDao().getAllGenres().map { it.toGenre() }

    override suspend fun getGenreWithMovies(): List<Pair<Genre, List<Movie>>> {
        Log.d("MovieViewModel", "getGenreWithMovies")
        val response = database.genreWithMoviesDao().getGenreWithMovies()
        val result: MutableList<Pair<Genre, List<Movie>>> = mutableListOf()

        response.forEach {
            result.add(Pair(it.genre.toGenre(), it.movies.map { movie -> movie.toMovie() }))
        }

        return result
    }

    override suspend fun insert(item: Any) {
        when (item) {
            is Movie -> database.moviesDao().insert(item.toEntity())
            is Genre -> database.genresDao().insert(item.toEntity())
            else -> error("WTF it can't be here")
        }
    }

    override suspend fun delete(item: Any) {
        when (item) {
            is Movie -> database.moviesDao().delete(item.toEntity())
            is Genre -> database.genresDao().delete(item.toEntity())
            else -> error("WTF it can't be here")
        }
    }

    override suspend fun insertGenreWithMovie(join: GenreMovieCrossReference) =
        database.genreWithMoviesDao().insertGenreWithMovies(join)
}