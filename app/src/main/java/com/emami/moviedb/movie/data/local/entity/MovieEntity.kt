package com.emami.moviedb.movie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "movie")
data class MovieEntity @JvmOverloads constructor(
    @PrimaryKey val id: Long,
    val popularity: Double,
    val voteCount: Int,
    val posterLink: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val title: String,
    val voteAverage: Double,
    val overview: String,
    val releaseDate: String,
    val createdAt: Instant = Instant.now()
)