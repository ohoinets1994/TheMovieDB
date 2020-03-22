package com.example.themoviewdb.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themoviewdb.extensions.load
import com.example.themoviewdb.model.Genre
import com.example.themoviewdb.model.Movie
import com.example.themoviewdb.repository.Repository
import com.example.themoviewdb.repository.database.GenreMovieCrossReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieViewModel(private val repository: Repository, private val dispatcher: CoroutineDispatcher) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = dispatcher

    val genreWithMovies = MutableLiveData<List<Pair<Genre, List<Movie>>>>()
    val error = MutableLiveData<Throwable>()

    fun fetchData() = load(error) {
        val response: List<Pair<Genre, List<Movie>>>
        response =
            if (repository.fetchPopularMovies(true).isEmpty()
                && repository.fetchMovieGenre(true).isEmpty()) {
                val networkMovies = repository.fetchPopularMovies()
                networkMovies.forEach { repository.insert(it) }

                val networkGenres = repository.fetchMovieGenre()
                networkGenres.forEach { repository.insert(it) }

                networkMovies.forEach { movie ->
                    movie.genre_ids.forEach { genreId ->
                        repository.insertGenreWithMovie(GenreMovieCrossReference(genreId, movie.id))
                    }
                }

                repository.getGenreWithMovies()
            } else {
                repository.getGenreWithMovies()
            }

        genreWithMovies.postValue(response)
    }
}