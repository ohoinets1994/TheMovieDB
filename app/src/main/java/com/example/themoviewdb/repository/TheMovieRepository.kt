package com.example.themoviewdb.repository

import android.content.Context
import com.example.themoviewdb.model.Genre
import com.example.themoviewdb.model.Movie
import com.example.themoviewdb.repository.database.DatabaseRepository
import com.example.themoviewdb.repository.database.GenreMovieCrossReference
import com.example.themoviewdb.repository.network.NetworkRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.net.UnknownHostException

class TheMovieRepository(private val context: Context) : Repository, KoinComponent {
    private val networkRepository by inject<NetworkRepository>()
    private val databaseRepository by inject<DatabaseRepository>()

    override suspend fun fetchPopularMovies(offlineOnly: Boolean): List<Movie> =
        execute(
            { networkRepository.fetchPopularMovies() },
            { databaseRepository.fetchPopularMovies() },
            offlineOnly
        )

    override suspend fun fetchMovieGenre(offlineOnly: Boolean): List<Genre> =
        execute(
            { networkRepository.fetchMovieGenre() },
            { databaseRepository.fetchMovieGenre() },
            offlineOnly
        )

    override suspend fun insertGenreWithMovie(join: GenreMovieCrossReference) =
        databaseRepository.insertGenreWithMovie(join)

    override suspend fun getGenreWithMovies(): List<Pair<Genre, List<Movie>>> =
        databaseRepository.getGenreWithMovies()

    override suspend fun insert(item: Any) = databaseRepository.insert(item)

    override suspend fun delete(item: Any) = databaseRepository.delete(item)

    private suspend fun <T> execute(
        online: suspend () -> T,
        offline: suspend () -> T,
        offlineOnly: Boolean = false
    ): T {
        return if (!offlineOnly) {
            try {
                online()
            } catch (e: UnknownHostException) {
                offline()
            }
        } else {
            offline()
        }
    }
}