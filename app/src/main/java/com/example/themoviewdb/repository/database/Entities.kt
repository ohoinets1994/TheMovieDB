package com.example.themoviewdb.repository.database

import androidx.room.*
import com.example.themoviewdb.model.Genre
import com.example.themoviewdb.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey() @ColumnInfo(name = "movieId") val movieId: Int,
    @ColumnInfo(name = "poster_path") val posterImg: String?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "backdrop_path") val backdropImg: String?
)

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey() @ColumnInfo(name = "genreId") val genreId: Int,
    @ColumnInfo(name = "name") val name: String
)

@Entity(tableName = "genreMovieCrossReference",
    primaryKeys = ["genreId", "movieId"])
data class GenreMovieCrossReference(
    val genreId: Int,
    val movieId: Int
)

data class GenreWithMovies(
    @Embedded val genre: GenreEntity,
    @Relation(
        parentColumn = "genreId",
        entity = MovieEntity::class,
        entityColumn = "movieId",
        associateBy = Junction(GenreMovieCrossReference::class)
    )
    val movies: List<MovieEntity>
)

fun Movie.toEntity() = MovieEntity(id, poster_path, title, backdrop_path)

fun MovieEntity.toMovie() = Movie(movieId, posterImg, listOf(), title, backdropImg)

fun Genre.toEntity() = GenreEntity(id, name)

fun GenreEntity.toGenre() = Genre(genreId, name)