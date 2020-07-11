package com.emami.moviedb.movie.data.local

import androidx.paging.PagingSource
import com.emami.moviedb.movie.data.local.entity.MovieEntity
import com.emami.moviedb.movie.data.local.entity.RemoteKeysEntity


interface LocalDataSource {
    suspend fun findRemoteKeyById(id: Long): RemoteKeysEntity?
    suspend fun clearAllTables()
    suspend fun insertAllKeys(list: List<RemoteKeysEntity>)
    suspend fun insertAllMovies(list: List<MovieEntity>)
    fun getAllMovies(): PagingSource<Int, MovieEntity>
}

