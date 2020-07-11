package com.emami.moviedb.forecast.data.network.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val video: Boolean,
    @SerializedName("poster_path") val posterPath: String,
    val id: Int,
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("genre_ids") val genres: List<Int>,
    val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String
)