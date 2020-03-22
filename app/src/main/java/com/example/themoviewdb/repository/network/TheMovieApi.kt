package com.example.themoviewdb.repository.network

import com.example.themoviewdb.model.GenreResponse
import com.example.themoviewdb.model.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface TheMovieApi {
    @GET("movie/popular")
    fun fetchPopularMovies(): Deferred<MovieResponse>

    @GET("genre/movie/list")
    fun fetchMovieGenre(): Deferred<GenreResponse>
}