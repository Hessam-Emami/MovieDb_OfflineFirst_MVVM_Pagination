package com.emami.moviedb.movie.data.network

import com.emami.moviedb.common.base.BaseDataSource
import com.emami.moviedb.common.util.DataResult
import com.emami.moviedb.common.util.DateTimeUtil
import com.emami.moviedb.movie.data.network.dto.MovieResponseDTO
import com.emami.moviedb.movie.util.MovieFilter
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val movieApi: MovieApi) : BaseDataSource(),
    RemoteDataSource {
    override suspend fun fetch(sort: MovieFilter.SORT, page: Int): DataResult<MovieResponseDTO> {
        return invokeApi {
            movieApi.discover(
                sortBy = sort.param, page = page, latestDate = DateTimeUtil.getFormattedDate()
            )
        }
    }
}