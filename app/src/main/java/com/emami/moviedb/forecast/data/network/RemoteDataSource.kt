package com.emami.moviedb.forecast.data.network

import com.emami.moviedb.common.base.BaseDataSource
import com.emami.moviedb.common.util.DataResult
import com.emami.moviedb.common.util.DateTimeUtil
import com.emami.moviedb.forecast.data.network.dto.MovieResponse
import com.emami.moviedb.forecast.util.MovieFilter
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun fetch(sort: MovieFilter.SORT, page: Int): DataResult<MovieResponse>
}

class RemoteDataSourceImpl @Inject constructor(private val movieApi: MovieApi) : BaseDataSource(),
    RemoteDataSource {
    override suspend fun fetch(sort: MovieFilter.SORT, page: Int): DataResult<MovieResponse> {
        return invokeApi {
            movieApi.discover(
                sortBy = sort.param, page = page, latestDate = DateTimeUtil.getFormattedDate()
            )
        }
    }
}