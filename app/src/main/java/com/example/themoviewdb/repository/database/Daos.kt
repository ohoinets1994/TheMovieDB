package com.example.themoviewdb.repository.database

import androidx.room.*

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE movieId = :id")
    fun getById(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)
}

@Dao
interface GenresDao {
    @Query("SELECT * FROM genres")
    fun getAllGenres(): List<GenreEntity>

    @Query("SELECT * FROM genres WHERE genreId = :id")
    fun getById(id: Int): GenreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg genre: GenreEntity)

    @Delete
    fun delete(genre: GenreEntity)
}

@Dao
interface GenreWithMovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGenreWithMovies(vararg join: GenreMovieCrossReference)

    @Transaction
    @Query("SELECT * FROM genres")
    fun getGenreWithMovies(): List<GenreWithMovies>
}