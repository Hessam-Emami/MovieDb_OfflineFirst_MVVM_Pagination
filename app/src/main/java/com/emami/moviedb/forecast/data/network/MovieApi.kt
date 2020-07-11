package com.emami.moviedb.forecast.data.network

import com.emami.moviedb.forecast.data.network.dto.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

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
    ): Response<MovieResponse>
}

