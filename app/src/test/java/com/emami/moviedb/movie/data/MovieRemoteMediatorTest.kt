package com.emami.moviedb.movie.data

import androidx.paging.LoadType
import com.emami.moviedb.movie.data.local.LocalDataSource
import com.emami.moviedb.movie.data.network.RemoteDataSource
import com.emami.moviedb.movie.util.MovieFilter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovieRemoteMediatorTest {

    val remoteDataSource = mock<RemoteDataSource>()
    val localDataSource = mock<LocalDataSource>()

    val remoteMediator = MovieRemoteMediator(
        MovieFilter.SORT.ASCENDING, remoteDataSource, localDataSource,
        mock()
    )

    @Test
    fun `should clear tables when refreshing`() = runBlockingTest {
        remoteMediator.load(LoadType.REFRESH, mock())

        verify(localDataSource).clearAllTables()
    }
}