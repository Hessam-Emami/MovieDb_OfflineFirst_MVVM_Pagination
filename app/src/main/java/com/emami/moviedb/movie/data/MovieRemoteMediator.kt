package com.emami.moviedb.movie.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.emami.moviedb.common.Constants
import com.emami.moviedb.common.db.MovieDatabase
import com.emami.moviedb.common.util.DataResult
import com.emami.moviedb.movie.data.local.LocalDataSource
import com.emami.moviedb.movie.data.local.entity.MovieEntity
import com.emami.moviedb.movie.data.local.entity.RemoteKeysEntity
import com.emami.moviedb.movie.data.local.mapper.toMovieEntity
import com.emami.moviedb.movie.data.network.RemoteDataSource
import com.emami.moviedb.movie.data.network.dto.MovieDTO
import com.emami.moviedb.movie.util.MovieFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val sortBy: MovieFilter.SORT,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val database: MovieDatabase
) : RemoteMediator<Int, MovieEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        //return because we are reading from cache and
        if (loadType == LoadType.PREPEND) {
            return MediatorResult.Success(
                endOfPaginationReached = true
            )
        }
        val page = computePageNumber(loadType, state)
        when (val apiResult = remoteDataSource.fetch(sort = sortBy, page = page)) {
            is DataResult.Success -> {
                val moviesFromNetwork = apiResult.data.results
                val endOfPaginationReached = moviesFromNetwork.isEmpty()
                database.withTransaction {
                    //Invalidate local cache if we are resubmitting paging
                    if (loadType == LoadType.REFRESH) {
                        localDataSource.clearAllTables()
                    }
                    insertNewPageData(moviesFromNetwork, endOfPaginationReached, page)

                }
                return MediatorResult.Success(
                    endOfPaginationReached = endOfPaginationReached
                )
            }
            is DataResult.Error -> {
                return MediatorResult.Error(
                    apiResult.e
                )
            }
        }

    }

    private suspend fun insertNewPageData(
        moviesFromNetwork: List<MovieDTO>,
        endOfPaginationReached: Boolean,
        page: Int
    ) {
        val nextKey = if (endOfPaginationReached) null else page + 1
        val keys = moviesFromNetwork.map {
            RemoteKeysEntity(
                repoId = it.id,
                nextKey = nextKey
            )
        }
        localDataSource.insertAllMovies(moviesFromNetwork.map { it.toMovieEntity() })
        localDataSource.insertAllKeys(keys)
    }

    private suspend fun getRemoteKeyFromLastItem(state: PagingState<Int, MovieEntity>): RemoteKeysEntity? =
        withContext(Dispatchers.IO) {
            state.lastItemOrNull()?.let {
                localDataSource.findRemoteKeyById(it.id)
            }
        }

    private suspend fun computePageNumber(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): Int =
        when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyFromCurrentPosition(state)
                //Return current page if exists, else return the default page: 1
                remoteKeys?.nextKey?.minus(1) ?: Constants.DEFAULT_PAGE_NUMBER
            }
            LoadType.APPEND -> {
                getRemoteKeyFromLastItem(state)?.nextKey
            }
            else -> null
        }
        //This can never be null, so throw an exception if somebody tried to mess with it.
            ?: throw RuntimeException("Problem within transactions, Page cannot be found.")


    private suspend fun getRemoteKeyFromCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKeysEntity? =
        withContext(Dispatchers.IO) {
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { movieId ->
                    localDataSource.findRemoteKeyById(movieId)
                }
            }
        }

}