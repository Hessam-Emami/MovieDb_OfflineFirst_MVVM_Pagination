package com.emami.moviedb.movie.data.network

import com.emami.moviedb.common.util.DataResult
import com.emami.moviedb.movie.data.network.dto.MovieResponseDTO
import com.emami.moviedb.movie.util.MovieFilter

interface RemoteDataSource {
    suspend fun fetch(sort: MovieFilter.SORT, page: Int): DataResult<MovieResponseDTO>
}

