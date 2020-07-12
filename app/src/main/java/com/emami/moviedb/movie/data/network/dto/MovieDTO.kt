package com.emami.moviedb.movie.data.network.dto

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    val id: Long,
    val title: String,
    val overview: String,
    val popularity: Double,
    val video: Boolean,
    val adult: Boolean,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("genre_ids") val genres: List<Int>,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String
) {
    val posterLink: String?
        get() =
            if (posterPath != null) "https://image.tmdb.org/t/p/w185$posterPath" else null
}

