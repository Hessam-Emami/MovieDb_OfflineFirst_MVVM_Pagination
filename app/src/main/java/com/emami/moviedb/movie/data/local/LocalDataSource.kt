package com.emami.moviedb.movie.data.local

import androidx.paging.PagingSource
import com.emami.moviedb.movie.data.local.entity.MovieEntity
import com.emami.moviedb.movie.data.local.entity.RemoteKeysEntity


interface LocalDataSource {
    fun getAllMovies(): PagingSource<Int, MovieEntity>
    suspend fun findRemoteKeyById(id: Long): RemoteKeysEntity?
    suspend fun findMovieById(id: Long): MovieEntity?
    suspend fun insertAllKeys(list: List<RemoteKeysEntity>)
    suspend fun insertAllMovies(list: List<MovieEntity>)
    suspend fun clearAllTables()
}

