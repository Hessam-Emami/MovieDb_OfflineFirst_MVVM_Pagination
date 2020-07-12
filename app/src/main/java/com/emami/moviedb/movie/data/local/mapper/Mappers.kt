package com.emami.moviedb.movie.data.local.mapper

import com.emami.moviedb.movie.data.local.entity.MovieEntity
import com.emami.moviedb.movie.data.network.dto.MovieDTO


fun MovieDTO.toMovieEntity(): MovieEntity {
    return MovieEntity(
        popularity = popularity,
        voteCount = voteCount,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        title = title,
        voteAverage = voteAverage,
        overview = overview,
        releaseDate = releaseDate,
        posterLink = posterLink
    )
}
