package com.emami.moviedb.movie.data.network

import com.emami.moviedb.movie.data.network.dto.MovieResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    suspend fun discover(
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
        @Query("primary_release_date.lte") latestDate: String,
        @Query("language") lang: String = "en",
        @Query("with_original_language") originalLang: String = "en",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") include_video: Boolean = true
    ): Response<MovieResponseDTO>
}

