package com.emami.moviedb.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.emami.moviedb.app.di.scope.ActivityScope
import com.emami.moviedb.movie.data.network.RemoteDataSourceImpl
import com.emami.moviedb.movie.data.local.entity.MovieEntity
import com.emami.moviedb.movie.data.local.LocalDataSource
import com.emami.moviedb.movie.data.network.RemoteDataSource
import com.emami.moviedb.movie.util.MovieFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import java.util.*
import javax.inject.Inject

/*
 No need to put this behind abstraction, We don't intend to create a fake repo and
 we can easily mock this in tests.
 */
@ActivityScope
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val pageConfig: PagingConfig
) {

    //Builds corresponding pagination sources
    fun fetchMovies(
        sortBy: MovieFilter.SORT
    ): LiveData<PagingData<MovieEntity>> {
        val localPagingSourceFactory = { localDataSource.getAllMovies() }
        val remoteMediator = MovieRemoteMediator(sortBy, remoteDataSource, localDataSource)
        return Pager(
            pageConfig,
            pagingSourceFactory = localPagingSourceFactory,
            remoteMediator = remoteMediator
        ).liveData
    }

    suspend fun getMovieById(id: Long): MovieEntity = withContext(Dispatchers.IO) {
        localDataSource.findMovieById(id)
            ?: throw RuntimeException("Movie with Id does not exist in page!")
    }
}

