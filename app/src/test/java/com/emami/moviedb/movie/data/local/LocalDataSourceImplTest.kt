package com.emami.moviedb.movie.data.local

import com.emami.moviedb.movie.data.local.dao.MovieDao
import com.emami.moviedb.movie.data.local.dao.RemoteKeysDao
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class LocalDataSourceImplTest {

    val movieDao = mock<MovieDao>()
    val keyDao = mock<RemoteKeysDao>()

    val dataSource = LocalDataSourceImpl(movieDao, keyDao)

    @ExperimentalCoroutinesApi
    @Test
    fun `should verify clear table behaviour`() = runBlockingTest {
        dataSource.clearAllTables()

        verify(movieDao).clearMovies()
        verify(keyDao).clearRemoteKeys()
    }


    @Test
    fun `should verify getAllMovies behaviour`() {
        dataSource.getAllMovies()

        verify(movieDao).getAllMovies()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should verify behaviour`() = runBlockingTest {
        dataSource.findRemoteKeyById(1)

        verify(keyDao).getRemoteKeyById(1)
    }
}