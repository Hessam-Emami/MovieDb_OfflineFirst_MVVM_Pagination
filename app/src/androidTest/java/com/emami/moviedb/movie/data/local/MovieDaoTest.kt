package com.emami.moviedb.movie.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.emami.moviedb.common.db.MovieDatabase
import com.emami.moviedb.movie.data.local.dao.MovieDao
import com.emami.moviedb.movie.data.local.entity.MovieEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalDataSourceTest {
    private lateinit var movieDao: MovieDao
    private lateinit var db: MovieDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun shouldSaveListAndFindById() = runBlocking {
        val movies = listOf<MovieEntity>(MovieEntity(111, 1.1, 1, "", "", "", "", 1.1, "", ""))

        movieDao.insertAll(movies)

        val movie = movieDao.getMovieById(111)
        Assert.assertNotNull(movie)
    }

    @Test
    fun shouldReturnNullWhenDaoEmpty() = runBlocking {

        Assert.assertNull(movieDao.getMovieById(333))
    }
}